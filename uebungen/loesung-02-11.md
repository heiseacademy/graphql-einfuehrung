# Mutations und Subscriptions

Wichtig: vor der Übung musst Du den `Authorization-Header` in GraphiQL setzen.

In der `README.md`-Datei findest Du Informationen, wie das geht

## Eine Mutation, mit der eine neue Story angelegt wird

Mit der `addStory`-Mutation kannst Du eine neue Story anlegen. 

Da diese Mutation einen _union type_ zurückliefert, musst Du mit `on`
explizit angeben aus welchem der zurückgelieferten Objekte du welche Felder
auslesen möchtest.

Wenn Du einen der Typen in deinem Query nicht abfragst, dieser aber zurückgeliefert
wird, wirst Du `null` zurückbekommen. 

Da `addStory` drei Typen zurückliefern kann, in der Mutation unten aber nur zwei
abgefragt werden, würde `null` zurückgeliefert, wenn der `AddStoryInvalidCredentialsPayload`
Typ als Antwort auf die Mutation kommt.

```graphql


mutation {
    addStory(input: {
        title: "Hello GraphQL"
        body: "GraphQL is a Query Language"
        tags: ["GraphQL", "Introduction"]
    }) {
        # Story konnte angelegt werden:
        ... on AddStorySuccessPayload {
            newStory {
                id
                writtenBy {
                    user {
                        id username
                    }
                }
            }
        } 
        
        # Story wurde nicht angelegt, weil die Daten ungültig
        # waren (z.B. Titel zu kurz):
        ...on AddStoryConstraintViolationPayload {
            violations {
                field
                message
            }
        }
    }
}


```

## Eine Subscription für Kommentare

```graphql

subscription {
    onNewComment(storyId: "Hier die Id aus der Mutation einsetzen") {
        newComment {
            id
            content
        }
    }
}

```

Die Subscription starten und das Browserfenster geöffnet lassen!


## Einen neuen Kommentar anlegen

Die Mutation zum Anlegen eines neuen Kommentars bitte in einem zweiten Browserfenster ausführen.

```graphql

mutation {
    addComment(input: {
        storyId: "Hier die Id aus der ersten Mutation eintragen",
        content: "Wonderful story!"
    }) {
        ... on AddCommentSuccessPayload {
            newComment {
                id
            }
        }
        ... on AddCommentFailurePayload {
            msg
        }
    }
}

```

Nachdem Du die Mutation ausgeführt hast (und sie erfolgreich war), sollte im ersten Browserfenster
deine Subscription nun ein Ergebnis anzeigen.