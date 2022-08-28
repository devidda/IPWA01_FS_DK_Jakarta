# Projekt zum Programmieren einer Webseite in Java

Verwendet werden das Jakarta Server Faces Framework, PrimeFaces und JPA.
Dieses Projekt folgt aus der Aufgabenstellung zweier Fallstudien in zwei Modulen der IU Internationalen Hochschule. Ein Modul beschäftigt sich mehr mit der generellen Webseiten-Programmierung z.B. mit JSF, während das andere die Datenhaltung per JPA zur Grundlage hat.

Im Folgenden der grobe Aufbau der 3-Schichten-Architektur:
![xlarge-dia](https://user-images.githubusercontent.com/75684697/187073767-c5c64c8b-8654-4bf6-9805-6a419f869ff4.png)


Auf der Startseite lassen sich die angezeigten Daten in der Tabelle sortieren und nach den Ländern suchen. Außerdem kann per Klick der zeitliche Verlauf der Menge des CO2-Ausstoßes eines Landes anzeigen.
![Hauptseite oben](https://user-images.githubusercontent.com/75684697/187069766-e776a3de-fd5d-4417-9a8a-c5f516490bf0.png)
![Hauptseite unten](https://user-images.githubusercontent.com/75684697/187069788-b96c4eab-8701-449e-b81a-773c347e999b.png)


Für den Login werden Daten aus einer einfachen lokalen JPA-Datenbank abgefragt.
![Login](https://user-images.githubusercontent.com/75684697/187070296-202b3ec5-2580-4647-bb93-b95641588ed1.png)


Im Verwaltungsbereich, der sich nur eingeloggt besuchen lässt, wird in einer weiteren Fallstudie die Möglichkeit zur Verwaltung der Daten mit einer weiteren Datenbank implementiert werden müssen.
![Verwaltung (management xhtml)](https://user-images.githubusercontent.com/75684697/167375585-9a83f360-a973-4e23-b11b-3229c13bee97.png)
