package main

import (
	influxdb2 "github.com/influxdata/influxdb-client-go/v2"
	"log"
	"time"

	"fyne.io/fyne/v2"
	"fyne.io/fyne/v2/app"
	"fyne.io/fyne/v2/container"
	"fyne.io/fyne/v2/layout"
	"fyne.io/fyne/v2/widget"
	_ "github.com/influxdata/influxdb-client-go/v2/api"
)

func main() {
	// Fyne-App erstellen
	myApp := app.New()

	// Fenster erstellen
	myWindow := myApp.NewWindow("Task Logger")

	// InfluxDB Konfiguration
	influxDBURL := "http://localhost:8086"   // Setze die URL deiner InfluxDB
	influxDBToken := "your-influxdb-token"   // Setze deinen InfluxDB Token
	influxDBOrg := "your-influxdb-org"       // Setze deine InfluxDB Organisation
	influxDBBucket := "your-influxdb-bucket" // Setze deinen InfluxDB Bucket

	// Input-Feld für den Task
	taskEntry := widget.NewEntry()
	taskEntry.SetPlaceHolder("Gib deinen Task ein")

	// Submit-Button
	submitButton := widget.NewButton("Submit", func() {
		// Den Task-Wert abrufen
		task := taskEntry.Text

		// Den Task in die InfluxDB schreiben
		writeToInfluxDB(task, influxDBURL, influxDBToken, influxDBOrg, influxDBBucket)

		// Bestätigung ausgeben
		log.Printf("Task '%s' erfolgreich in die InfluxDB geschrieben.\n", task)

		// Input-Feld leeren
		taskEntry.SetText("")
	})

	// UI layout
	content := container.NewVBox(
		taskEntry,
		submitButton,
	)

	myWindow.SetContent(container.New(layout.NewCenterLayout(), content))
	myWindow.Resize(fyne.NewSize(300, 150))
	myWindow.ShowAndRun()
}

// Funktion zum Schreiben in die InfluxDB
func writeToInfluxDB(task, url, token, org, bucket string) {
	// InfluxDB Client konfigurieren
	client := influxdb2.NewClient(url, token)
	defer client.Close()

	// Write-API erstellen
	writeAPI := client.WriteAPI(org, bucket)

	// Datenpunkt erstellen
	p := influxdb2.NewPoint(
		"tasks",                              // Measurement
		map[string]string{},                  // Tags
		map[string]interface{}{"task": task}, // Felder
		time.Now(),                           // Zeitstempel
	)

	// Datenpunkt in die InfluxDB schreiben
	writeAPI.WritePoint(p)
	// Flushen, um sicherzustellen, dass alle Daten geschrieben werden
	writeAPI.Flush()
}
