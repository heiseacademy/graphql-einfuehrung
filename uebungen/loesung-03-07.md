# Paginierung

## Schritt 1: Mit Seiten-basierter Paginierung den User **Deven Purdy** finden.

Den folgenden Query musst Du insgesamt zweimal ausführen (erst `page: 1`, dann `page: 2`), auf Seite 2
ist dann der User **Deven Purdy** zufinden:

```graphql
{
    members(size: 2, page: 2) {
        content {
            id
            user {
                name
                username
            }
        }
    }
}
```

## Die Stories von Deven Purdy ausgeben

Mit der Id des Benutzers Deven Purdy (`bWVtYmVyOjQ=`), kannst Du nun dessen Stories abfragen:

```graphql
{
  stories(last: 3, 
      orderBy: {field: reactions, direction: desc}
      condition: {field: writtenBy, value: "bWVtYmVyOjQ="}) {
    edges {
      # Mit dem zurückgelieferten Cursor könntest Du die
      # Stories vor- bzw. hinter dieser Story abfragen  
      cursor  
      node {
        id title
        reactionSummary {
          totalCount
        }
      }
    }
  }
}
```