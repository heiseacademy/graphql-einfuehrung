package nh.publy.backend.graphql;

import static java.lang.String.*;

public class InvalidIdFormatException extends RuntimeException {
  public InvalidIdFormatException(String id) {
    super(format("Specified id '%s' has invalid format", id));
  }
}
