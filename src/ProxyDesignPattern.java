interface Document {
    String read();
}

class RealDocument implements Document {
    private String content;
    RealDocument(String content) {
        this.content = content;
        System.out.println("Loading document from disk... (expensive)");
    }
    public String read() { return content; }
}

class VirtualProxyDocument implements Document {
    private RealDocument realDoc;
    private final String content;
    VirtualProxyDocument(String content) { this.content = content; }
    public String read() {
        if (realDoc == null) realDoc = new RealDocument(content);
        return realDoc.read();
    }
}

class ProtectionProxyDocument implements Document {
    private RealDocument realDoc;
    private String userRole;
    ProtectionProxyDocument(String content, String userRole) {
        this.realDoc = new RealDocument(content);
        this.userRole = userRole;
    }
    public String read() {
        if (!userRole.equals("ADMIN")) throw new SecurityException("Access denied: insufficient permissions");
        return realDoc.read();
    }
}

class RemoteDocumentProxy implements Document {
    private String documentId;
    RemoteDocumentProxy(String documentId) { this.documentId = documentId; }
    public String read() {
        System.out.println("Fetching from remote server...");
        return fetchFromServer(documentId);
    }
    private String fetchFromServer(String id) {
        return "Remote content for doc: " + id;
    }
}

class CachingProxyDocument implements Document {
    private RealDocument realDoc;
    private String cachedContent;
    private String content;
    CachingProxyDocument(String content) { this.content = content; }
    public String read() {
        if (cachedContent == null) {
            realDoc = new RealDocument(content);
            cachedContent = realDoc.read();
        } else {
            System.out.println("Returning cached result");
        }
        return cachedContent;
    }
}

class LoggingProxyDocument implements Document {
    private RealDocument realDoc;
    LoggingProxyDocument(String content) { realDoc = new RealDocument(content); }
    public String read() {
        System.out.println("LOG: read() called at " + System.currentTimeMillis());
        String result = realDoc.read();
        System.out.println("LOG: read() completed");
        return result;
    }
}

public class ProxyDesignPattern {
    public static void main(String[] args) {
        System.out.println("--- Virtual Proxy ---");
        Document virtual = new VirtualProxyDocument("Hello World");
        System.out.println(virtual.read());

        System.out.println("\n--- Protection Proxy ---");
        Document protectedDoc = new ProtectionProxyDocument("Secret Data", "ADMIN");
        System.out.println(protectedDoc.read());

        System.out.println("\n--- Remote Proxy ---");
        Document remote = new RemoteDocumentProxy("doc-101");
        System.out.println(remote.read());

        System.out.println("\n--- Caching Proxy ---");
        Document caching = new CachingProxyDocument("Cached Content");
        System.out.println(caching.read());
        System.out.println(caching.read());

        System.out.println("\n--- Logging Proxy ---");
        Document logging = new LoggingProxyDocument("Logged Content");
        System.out.println(logging.read());
    }
}