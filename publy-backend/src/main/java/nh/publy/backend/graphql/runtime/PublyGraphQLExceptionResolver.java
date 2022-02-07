package nh.publy.backend.graphql.runtime;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import nh.publy.backend.graphql.InvalidIdFormatException;
import nh.publy.backend.graphql.InvalidPaginationDataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.stereotype.Component;

@Component
public class PublyGraphQLExceptionResolver extends DataFetcherExceptionResolverAdapter {

  private static final Logger log = LoggerFactory.getLogger(PublyGraphQLExceptionResolver.class);

  @Override
  protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
    Throwable t = NestedExceptionUtils.getMostSpecificCause(ex);
    if (t instanceof InvalidPaginationDataException) {
      return GraphqlErrorBuilder.newError(env)
        .errorType(PublyErrorType.InvalidPaginationBoundaries)
        .message(t.getMessage())
        .build();
    }

    if (t instanceof InvalidIdFormatException) {
      return GraphqlErrorBuilder.newError(env)
        .errorType(PublyErrorType.InvalidIdFormat)
        .message(t.getMessage())
        .build();
    }

    return null;
  }
}
