package nh.publy.backend.graphql;

import java.util.Base64;

import static nh.publy.backend.graphql.DemoConfig.useComplexNodeIds;

public class NodeId {
  private NodeType nodeType;
  private Long id;

  public static NodeId parse(Object value) {
    if (value == null) {
      throw new IllegalArgumentException("value of a NodeId must not be null");
    }

    return new NodeId(String.valueOf(value));
  }

  public NodeId(String id) {
    if (!useComplexNodeIds) {
      this.nodeType = null;
      this.id = Long.parseLong(id);
    } else {
      try {
        String decoded = decode(id);
        String[] parts = decoded.split(":");
        if (parts.length != 2) {
          throw new InvalidIdFormatException(id);
        }

        String typeName = parts[0];
        this.nodeType = NodeType.valueOf(typeName);
        String rawValue = parts[1];
        this.id = parseRawValue(rawValue);

      } catch (Exception ex) {
        throw new InvalidIdFormatException(id);
      }
    }
  }

  public NodeId(Long id, NodeType nodeType) {
    this.id = id;
    this.nodeType = nodeType;
  }

  public static NodeId forMember(Long id) {
    return new NodeId(id, NodeType.member);
  }

  public NodeId expectType(NodeType expectedNodeType) {
    if (!useComplexNodeIds) {
      return this;
    }
    if (!this.nodeType.equals(expectedNodeType)) {
      throw new InvalidIdFormatException(String.valueOf(this.id));
    }
    return this;
  }

  public Long getId() {
    return id;
  }

  public NodeType getNodeType() {
    return nodeType;
  }

  private Long parseRawValue(String rawValue) {
    return Long.parseLong(rawValue);
  }

  private static String decode(String base64String) {
    return new String(Base64.getDecoder().decode(base64String));
  }

  public String toString() {
    if (!useComplexNodeIds) {
      return getId().toString();
    }
    String idString = this.nodeType.name() + ":" + getId();
    return new String(Base64.getEncoder().encodeToString(idString.getBytes()));
  }
}
