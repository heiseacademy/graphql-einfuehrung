## heise Academy GraphQL Kurs

# Publy, eine GraphQL Beispiel-Anwendung


# 1. Öffnen und Starten der Anwendung

![Screenshot der Publy Beispiel Anwendung](./publy-screenshot.png)

Du kannst die Beispiel-Anwendung auf zwei Arten nutzen:

- in der Online IDE GitPod (Vorteil: Du brauchst nichts weiter zu installieren)
- bei dir lokal

## Öffnen der Anwendung in GitPod

Um die Anwendung mit der Online IDE [GitPod](https://gitpod.io) zu öffnen,
klicke unten auf den "Open in GitHub" Button. Du brauchst einen GitPod Benutzeraccount, dieser ist aber in der einfachsten Variante kostenlos. 

Damit bekommst dann eine komplette Entwicklungsumgebung in der Cloud, die sich in deinem Browser öffnet und
die alle benötigten Tools enthält.

![Die Beispiel-Anwendung im GitPod Workspace](./gitpod-screenshot.png)

Beim Öffnen deines Workspaces über den Button wird die Anwendung automatisch
gestartet und gebaut. Sobald das abgeschlossen ist, öffnen sich zwei weitere Browserfenster:

- GraphiQL
- Frontend

Bitte beachte, dass das Starten ein paar Minuten dauern kann, bis der Workspace fertig eingerichtet ist.

Achte bitte darauf, dass Du **deinem Browser erlaubst, Pop-up-Fenster von GitPod zu öffnen**, andernfalls kann GitPod
die benötigten Fenster nicht öffnen!

[![Open in Gitpod](https://gitpod.io/button/open-in-gitpod.svg)](https://gitpod.io/#https://github.com/heiseacademy/graphql-einfuehrung)

## URLs und Hostnamen in GitPod

**Achtung**: Die von GitPod verwendeten Hostnamen sind in jedem Workspace unterschiedlich. Falls Du
(versehentlich) ein Browserfenster geschlossen hast, öffne den "Remote Explorer" in GitPod:

![Der GitPod Remote Explorer](./gitpod-remote-explorer.png)

Dort findest Du eine Liste aller Ports der Anwendung. 
Für jeden Port kannst Du wieder einen Browser öffnen lassen, in dem Du
mit der Maus über den Port fährst, und dann das Browser-Symbol anklickst:

![Der GitPod Remote Explorer](./gitpod-open-browser.png)

Verwendete Ports der Anwendung:

* 8080: GraphiQL
* 3000: Publy Frontend
* 8081: UserService (keine Web-Oberfläche)
* 8432: Postgres (keine Web-Oberfläche)

## Lokales Starten der Anwendung

**Voraussetzungen:**

- Java 17
- Docker
- [yarn](https://yarnpkg.com/) optional, nur für das Frontend

**Schritt 1: Repository klonen**

- Bitte klone zunächst dieses Repository mit deinem Git Client.

**Schritt 2: Datenbank starten (mit Docker)**

Im Root-Verzeichnis des Repositories:

- `docker-compose -f docker-compose -f docker-compose.yml up -d`

**Schritt 3: Userservice starten**

Achtung! Port 8081 darf nicht belegt sein:

- `./gradlew publy-userservice:bootRun`
  
**Schritt 4: Backend starten**

Achtung! Port 8080 darf nicht belegt sein:

- `./gradlew publy-backend:bootRun`

Du kannst nun über `http://localhost:8080` den GraphiQL-Explorer
aufmachen und Queries und Mutations ausführen.

**Schritt 5: Frontend starten (optional)**

Achtung! Port 3000 darf nicht belegt sein:

```bash

cd publy-frontend

yarn install

yarn start
```

Du kannst nun das Frontend über `http://localhost:3000` in deinem Browser öffnen.

## Öffnen in der IDE

Du kannst das Root-Verzeichnis in IDEA öffnen, dann wird das Projekt als Gradle-Projekt erkannt und von IDEA automatisch richtig konfiguriert.

Dann kannst Du u.a. die beiden Server-Prozesse als Java-Anwendungen starten:

- `nh.graphql.publy.userservice.UserserviceApplication`: Der User-Service
- `nh.publy.backend.PublyApplication`: Das (GraphQL-)Backend

Wichtig: In jedem Fall bitte zuerst die Datenbank wie oben beschrieben mit `docker-compose` starten!

# 2. Frontend verwenden

Wenn Du das Beispiel-Frontend ausprobieren möchte, öffne es in deinem Browser unter `http://localhost:3000` (oder dem entsprechendem
Hostnamen in GitPod, s.o.)

Als nicht angemeldeter Benutzer darfst Du z.B. Artikel und Kommentare lesen sowie Benutzer ("Member")-Informationen anzeigen. Im Hintergrund werden dann GraphQL **Queries** ausgeführt,
die Du z.B. mit der Netzwerk-Konsole deines Browsers ansehen kannst.

Wenn Du einen Kommentar hinzufügen möchtest oder eine Reaktion hinterlassen willst (beides als **Mutations**) implementiert,
musst Du dich über die Login-Seite einloggen. Dort findest Du Benutzernamen, die Du verwenden kannst.

# 3. Ausführen von Queries in GraphiQL

Du kannst GraphiQL benutzen, um GraphQL Queries mit der Beispiel-Anwendung auszuführen. GraphiQL kannst Du auf Port 8080
in deinem Browser öffnen.

Beispiel 1:

```graphql
query {
    stories(first: 10) {
        edges {
            node {
                id
                title
                body
            }
        }
    }
}
```

## Authorization-Tokens (für Mutations)

Wenn Du **Mutations** ausführen willst, erwartet das Backend einen Benutzer-Token.
Zum Testen kannst Du einen der folgenden Tokens verwenden, die immer gültig sind:

- User: `nils`
- Rolle: `ROLE_USER` (darf alles)

```json
{"Authorization": "Bearer eyJhbGciOiJSUzUxMiJ9.eyJzdWIiOiJ7XCJpZFwiOlwiVTFcIixcInVzZXJuYW1lXCI6XCJuaWxzXCIsXCJuYW1lXCI6XCJOaWxzIEhhcnRtYW5uXCIsXCJlbWFpbFwiOlwia29udGFrdEBuaWxzaGFydG1hbm4ubmV0XCIsXCJyb2xlc1wiOltcIlJPTEVfVVNFUlwiXX0iLCJpYXQiOjE2NDEzNjk4NDcsImV4cCI6MzIzMjk4Nzk4N30.cXJh6llnkkYKRVXdtPCbjWhnEshbI1LnMVc9aZpwzk5j16EC3pLU5A5kgwH4JIZvl5aoMBfW9Wsdgll79kFY22bQvUQIGnZTyn9LZVFKWtyXttq4ZdA4eP64yQkcVRFoD3kCWSLRCzK97z4moGbhqfHGB1MjPvuYS7-SsdTtGGmit9w4jsaIHgisO5v8V9yMFsdYvQdNbzRMXTE6svLbLsVyCS5cA9-BycCvy5kNA2DTNPHZOZAFPqFEe3jbHUetmgXSSA2uEZyOUrMCxtSoVjS7vld1OKZZ1Z946Oj8rXbMz3O-4Ht8slEr5CX1ENB4MEIyBOZc8NuZ-iLFUuLJTA"}
```

- User: `murphy`
- Rolle: `ROLE_GUEST` (darf Kommentare und Reaktion geben, aber keine Stories erzeugen. Hinweis: Stories erzeugen nur über GraphiQL möglich, nicht über das Frontend)

```json
{"Authorization": "Bearer eyJhbGciOiJSUzUxMiJ9.eyJzdWIiOiJ7XCJpZFwiOlwiVTdcIixcInVzZXJuYW1lXCI6XCJtdXJwaHlcIixcIm5hbWVcIjpcIkV0aGVseW4gTXVycGh5XCIsXCJlbWFpbFwiOlwiZXRoZWx5bi5tdXJwaHlAZXhhbXBsZS5jb21cIixcInJvbGVzXCI6W1wiUk9MRV9HVUVTVFwiXSxcImd1ZXN0XCI6dHJ1ZX0iLCJpYXQiOjE2NDEzNjk4NDcsImV4cCI6MzIzMjk4Nzk4N30.MeggHVMDJLAy61TksL1yGZQsnuFyXgWd1NP4Q0QOylU0g_NJX9hUz3QvfRa4upfa-fEMlt-4cG6xOB3R_GNGxinvRBGmmPK8tPY7n2fB5RpR6KRr0hS5FcWwfth2obnt_puKlobEgpBv-1vwA_Z4cyjWoUiQ2DKRQ9AIfQ_qRacXjJs4--bEV8RpuCOlt5Ap9GMyaEZPvlELERD9-ZzXSdU599pMu3ZWBrTFUe0d2JNVlDK4ASIchuKHIygt6v0ohxFlbjS_DAmRNnXA0-HmiU7a7ERCTS2-2DzsJrW58oWMZCtKd1xekw95XG4tymAWU8ScJvdwEPR_K0rTzGCCFQ"}
```

Den kompletten Authorization-JSON-String kannst Du in GraphiQL unten in den "**Request Headers**"-Tab einfügen.
Der Header wird dann bei jedem Query/jeder Mutation mitgesendet.

[![Request Header in GraphiQL setzen]](./graphiql-request-header.png)

Du kannst dann z.B. den angemeldeten Benutzer (der durch den Token repräsentiert wird),
abfragen:

```graphql
query {
    me {
        id
        user {
            name
            email
        }
        
        bio
        skills
        currentlyLearning
    }
}
```

# Links

* GraphQL Homepage: https://graphql.org/
* IntelliJ Plug-in für GraphQL: https://plugins.jetbrains.com/plugin/8097-graphql
* GraphQL Extension für VS Code: https://marketplace.visualstudio.com/items?itemName=GraphQL.vscode-graphql
* GraphQL Queries mit Postman ausführen: https://learning.postman.com/docs/sending-requests/supported-api-frameworks/graphql/
* GraphiQL: https://github.com/graphql/graphiql
* Liste von Frameworks für diverse Programmiersprachen, mit denen Du GraphQL APIs bauen kannst: https://graphql.org/code/#language-support
* Cursor-basierte Paginierung: https://graphql.org/learn/pagination/
* Relay Cursor Specification: https://relay.dev/graphql/connections.htm
* Postgraphile: https://www.graphile.org/postgraphile/

# Lösungen für die Übungen

Lösungsmöglichkeiten für die einzelnen Übungen findest Du im `uebungen`-Verzeichnis.

