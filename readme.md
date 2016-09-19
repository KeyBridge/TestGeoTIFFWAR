# TestGeoTIFFWAR

This is a very simple WAR program that to demonstrate an issue encountered when
trying to read a GeoTIFF file from within the GlassFish JavaEE container.

This program works under Tomcat (8.0.27) but FAILS under Glassfish / Payara (4.1.1).

## What it does:

 1. Read a GeoTIFF file.
 2. Print a pixel value.

## What's supposed to happen:

 - Trigger the REST resource by calling the URI pattern.
 - The rest method should log all actions.

## What actually happens:

Tomcat: Appears to work OK.

Glassfish / Payara: GeoTiffReader fails to initialize. Application crashes.

## What appears to be the problem:

A stack trace and debugging session appear to show an issue when the GeoTiffReader
attempts to read the file metadata and mask.

Also, the CRS factory (e.g. CRS.decode ) appears to kill the JavaEE logger service. (!)

However, because the JavaEE logger is affected it is very difficult / impossible
to know what is crashing the application under Glassfish / Payara.

Any help getting this to work would be greatly appreciated!

## Reward:

Contact: info@keybridgeglobal.com with ideas / suggestions / offers.