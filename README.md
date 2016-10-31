# About

The Alfresco ECM product provides a basic multi-tenancy capability that users can utilize to logically keep different user bases and their content separate within an Alfresco installation. The support for multi-tenancy has had long standing [limitations](http://docs.alfresco.com/5.1/concepts/mt-not-implemented.html) that restrict the usefulness of the overal platform.

This module aims to provide - over time - alternative subsystem implementations that fully support the multi-tenancy capabilites of Alfresco and allow engineers / administrators to set up a system to use the same kind of functionality available to "simple" installs.

# Roadmap

Alfresco itself currently considers several features not to be supported in a multi-tenant environment. This module aims to support most of them eventually:

 - LDAP (next release)
 - Inbound email (near future)
 - External authentication (near future)
 - IMAP (to be investigated)
 - Smart Folders (to be investigated)
 - Content replication (to be investigated)
 - Kerberos (to be investigated)
 - CIFS (not planned due to legacy SMB protocol support in Alfresco)
 - NTLM (not planned due to being an obsolete / insecure SSO mechanism)
 - Records Management (not planned due to extensive scope)
 - Encrypted content store (not planned - should already be supported via [simple-content-stores](https://github.com/AFaust/simple-content-stores))
 
# Maven usage

This addon is being built using the [Acosix Alfresco Maven framework](https://github.com/Acosix/alfresco-maven) and produces both AMP and installable JAR artifacts. Depending on the setup of a project that wants to include the addon, different approaches can be used to include it in the build.

## Build

This project can be build simply by executing the standard Maven build lifecycles for package, install or deploy depending on the intent for further processing. A Java Development Kit (JDK) version 8 or higher is required for the build.

## Dependency in Alfresco SDK

The simplest option to include the addon in an All-in-One project is by declaring a dependency to the installable JAR artifact. Alternatively, the AMP package may be included which typically requires additional configuration in addition to the dependency.

### Repository
```xml
<dependency>
    <groupId>de.acosix.alfresco.mtsupport</groupId>
    <artifactId>de.acosix.alfresco.mtsupport.repo</artifactId>
    <version>1.0.0.0-SNAPSHOT</version>
    <classifier>installable</classifier>
</dependency>

<!-- OR -->

<dependency>
    <groupId>de.acosix.alfresco.mtsupport</groupId>
    <artifactId>de.acosix.alfresco.mtsupport.repo</artifactId>
    <version>1.0.0.0-SNAPSHOT</version>
    <type>amp</type>
</dependency>

<plugin>
    <artifactId>maven-war-plugin</artifactId>
    <configuration>
        <overlays>
            <overlay />
            <overlay>
                <groupId>${alfresco.groupId}</groupId>
                <artifactId>${alfresco.repo.artifactId}</artifactId>
                <type>war</type>
                <excludes />
            </overlay>
            <!-- other AMPs -->
            <overlay>
                <groupId>de.acosix.alfresco.mtsupport</groupId>
                <artifactId>de.acosix.alfresco.mtsupport.repo</artifactId>
                <version>1.0.0.0-SNAPSHOT</version>
            </overlay>
        </overlays>
    </configuration>
</plugin>
```

For Alfresco SDK 3 beta users:

```xml
<platformModules>
    <moduleDependency>
        <groupId>de.acosix.alfresco.mtsupport</groupId>
        <artifactId>de.acosix.alfresco.mtsupport.repo</artifactId>
        <version>1.0.0.0-SNAPSHOT</version>
        <type>amp</type>
    </moduleDependency>
</platformModules>
```

### Share
```xml
<dependency>
    <groupId>de.acosix.alfresco.mtsupport</groupId>
    <artifactId>de.acosix.alfresco.mtsupport.share</artifactId>
    <version>1.0.0.0-SNAPSHOT</version>
    <classifier>installable</classifier>
</dependency>

<!-- OR -->

<dependency>
    <groupId>de.acosix.alfresco.mtsupport</groupId>
    <artifactId>de.acosix.alfresco.mtsupport.share</artifactId>
    <version>1.0.0.0-SNAPSHOT</version>
    <type>amp</type>
</dependency>

<plugin>
    <artifactId>maven-war-plugin</artifactId>
    <configuration>
        <overlays>
            <overlay />
            <overlay>
                <groupId>${alfresco.groupId}</groupId>
                <artifactId>${alfresco.share.artifactId}</artifactId>
                <type>war</type>
                <excludes />
            </overlay>
            <!-- other AMPs -->
            <overlay>
                <groupId>de.acosix.alfresco.mtsupport</groupId>
                <artifactId>de.acosix.alfresco.mtsupport.share</artifactId>
                <version>1.0.0.0-SNAPSHOT</version>
            </overlay>
        </overlays>
    </configuration>
</plugin>
```

TODO: SDK 3 usage