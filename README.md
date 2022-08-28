# Projekt zum Programmieren einer Webseite in Java

Verwendet werden das Jakarta Server Faces Framework, PrimeFaces und JPA.
Dieses Projekt folgt aus der Aufgabenstellung zweier Fallstudien in zwei Modulen der IU Internationalen Hochschule. Ein Modul beschäftigt sich mehr mit der generellen Webseiten-Programmierung z.B. mit JSF, während das andere die Datenhaltung per JPA zur Grundlage hat.

## Aufbau
Im Folgenden ein UML-Diagramm zur Veranschaulichung der 3-Schichten-Architektur der Webseite:
![xlarge-dia](https://user-images.githubusercontent.com/75684697/187073767-c5c64c8b-8654-4bf6-9805-6a419f869ff4.png)

## Hauptseite (data.xhtml)
Auf der Hauptseite werden Daten zu CO2-Emissionen aller Länder der lokalen Datenbank veranschaulicht. Es lässt sich u.a. nach Ländern filtern, nach Emissionsmenge sortieren, der aktuellste Wert anzeigen oder der zeitliche Verlauf eines Landes mit einem Liniendiagramm darstellen.
![Hauptseite oben](https://user-images.githubusercontent.com/75684697/187069766-e776a3de-fd5d-4417-9a8a-c5f516490bf0.png)
![Hauptseite unten](https://user-images.githubusercontent.com/75684697/187069788-b96c4eab-8701-449e-b81a-773c347e999b.png)

## Login (login.xhtml)
Für den Login werden Daten aus einer einfachen lokalen JPA-Datenbank abgefragt und damit die Eingabe vbalidiert.
![Login](https://user-images.githubusercontent.com/75684697/187070296-202b3ec5-2580-4647-bb93-b95641588ed1.png)

## Management (management.xhtml)
Im Verwaltungsbereich, der sich nur eingeloggt besuchen lässt, kann die der Hauptseite zu Grunde liegende Datenbank bearbeitet werden.
![Verwaltung (management xhtml)](https://user-images.githubusercontent.com/75684697/167375585-9a83f360-a973-4e23-b11b-3229c13bee97.png)
