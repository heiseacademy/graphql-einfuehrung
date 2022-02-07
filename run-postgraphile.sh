#! /bin/bash

npx postgraphile -c postgres://klaus:secretpw@localhost:8432/publy_db --enhance-graphiql --allow-explain --watch
