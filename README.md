<p align="left">
  <img src="https://raw.githubusercontent.com/arroznatwitch/PortalHelper/master/src/main/resources/icon/icon.png" width="64" alt="PortalHelper icon">
</p>

# PortalHelper

A simple Minecraft mod that helps you convert Overworld coordinates to Nether coordinates, and vice versa.

## Usage

```
/portal <world> <coords>
```

`<world>` is `overworld` or `nether`, `<coords>` in `x,y,z` format. Returns the matching coordinates in the other world.

## Run and Build

```bash
git clone https://github.com/arroznatwitch/PortalHelper.git
cd PortalHelper
./gradlew runClient   # opens a client with the mod loaded
./gradlew build        # jar ends up in build/libs/
```

Entry point: `com.portalhelper.PortalHelper`

## Stack

Java 21+, Fabric Loader, Loom 1.16
