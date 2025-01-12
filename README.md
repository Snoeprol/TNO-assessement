# TNO-assessment

## Simulatie Uitvoeren

0. Clone de repo:

`git clone https://github.com/Snoeprol/TNO-assessement/`

1. Navigeer naar de single-file-solution directory:

`cd single-file-solution`

2. Compileer het Java bestand:

`javac PatriotSimulation.java`

3. Voer het gecompileerde programma uit:

`java PatriotSimulation 0`

voor interpretatio 0, zie Interpretatie (0).

`java PatriotSimulation 1`

voor interpretatio 1, zie Interpretatie (1).

## Interpretatie (0)

Deze methode analyseert elke radar-outputwaarde afzonderlijk door het laatste cijfer te controleren:

Voor elke radaruitlezing (gesplitst op basis van ;) in de array:

Als de waarde eindigt op '1', wordt deze geteld als een oneven signaal
Als de waarde eindigt op '0', wordt deze geteld als een even signaal

```
Voorbeeld:
CopyInvoer: ["1001", "1011", "1010"]
Analyse:
- "1001" eindigt op 1 → oneven telling +1
- "1011" eindigt op 1 → oneven telling +1
- "1010" eindigt op 0 → even telling +1
Resultaat: oneven telling (2) > even telling (1) → Vijand gedetecteerd
```

## Interpretatie (1)

Deze methode voert de volgende analyse uit:

1. Voegt alle binaire reeksen samen tot één lang binair getal
2. Zet het resulterende binaire getal om naar decimaal
3. Analyseert de frequentie van oneven en even cijfers in de decimale weergave


Voorbeeld:

```
Invoer: ["1001", "1011", "1010"]
Stappen:
1. Samengevoegd binair: "100110111010"
2. Decimale conversie: 2490
3. Cijferanalyse:
   - 2 (even)
   - 4 (even)
   - 9 (oneven)
   - 0 (even)
Resultaat: oneven telling (2) > even telling (1) → Vijand gedetecteerd
```
De simulatie output wordt getoond in de terminal, met:
- Tijdstappen
- Radar data output
- Vijand detectie status
- Raket lancering resultaten (indien vijand gedetecteerd)

## Programma Beschrijving

Dit programma simuleert een Patriot raketafweersysteem dat:
- Radar data leest vanuit een CSV bestand
- De data interpreteert om potentiële dreigingen te detecteren
- Onderscheppingsraketten lanceert wanneer dreigingen worden gedetecteerd
- Hit/miss kans simuleert gebaseerd op een PK ratio van 0,8

## Vereisten

- Java Development Kit (JDK) geïnstalleerd
- radar_data.csv bestand aanwezig in de ./single_file_solution/data/ directory