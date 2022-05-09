# IPWA01_FS_DK_Jakarta
Eine Website im Jakarta Server Faces Framework mit PrimeFaces und JPA.
Dieses Projekt folgt aus der Aufgabenstellung einer Fallstudie für das Modul "Programmierung von Web-Anwendungsoberflächen" (IPWA01) der IU Internationale Hochschule.
Code-Kommentare werden noch eingefügt.


Im Folgenden der grobe Aufbau der 3-Schichten-Architektur:

![3-Schichten-Architektur](3-Schichten-Architektur.png)

Auf der Startseite lassen sich die angezeigten Daten in der Tabelle sortieren und nach den Ländern suchen. Außerdem kann per Klick der zeitliche Verlauf der Menge des CO2-Ausstoßes eines Landes anzeigen.
![Startseite (index xhtml)](https://user-images.githubusercontent.com/75684697/167374676-dacf7c93-92bd-4da9-a71e-d068e22b4725.png)

Für den Login werden Daten aus einer einfachen lokalen JPA-Datenbank abgefragt.
![Login (login xhtml)](https://user-images.githubusercontent.com/75684697/167375328-9b6c2243-2c79-4cd2-aac5-5ab12b76279c.png)

Im Verwaltungsbereich, der sich nur eingeloggt besuchen lässt, wird in einer weiteren Fallstudie die Möglichkeit zur Verwaltung der Daten mit einer weiteren Datenbank implementiert werden müssen.
![Verwaltung (management xhtml)](https://user-images.githubusercontent.com/75684697/167375585-9a83f360-a973-4e23-b11b-3229c13bee97.png)
