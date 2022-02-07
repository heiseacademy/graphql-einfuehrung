# Ein erster GraphQL Query mit GraphiQL

```graphql

query {
    story {
        id
        title
        
        writtenBy {
            user {
                username
            }
        }
    }
}


```