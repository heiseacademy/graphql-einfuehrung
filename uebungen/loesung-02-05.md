# GraphQL Queries

```graphql

fragment CommentFragment on Comment {
    id
    content
    author:writtenBy {
        id
        user { username }
    }
}

query {
    oldestStories: stories(first:3) {
        nodes {
            id
            createdAt
            comments(first:3) {
                nodes {
                    ...CommentFragment
                }
            }
        }
    }

    newestStories: stories(last:3) {
        nodes {
            id
            createdAt
            comments(first:3) {
                nodes {
                    ...CommentFragment
                }
            }
        }
    }
}



```