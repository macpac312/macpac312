#!/bin/bash
# This script converts the SVG icon to PNG files at different sizes
# Requires ImageMagick or rsvg-convert

if command -v rsvg-convert &> /dev/null; then
    rsvg-convert -w 16 -h 16 icons/icon.svg -o icons/icon16.png
    rsvg-convert -w 32 -h 32 icons/icon.svg -o icons/icon32.png
    rsvg-convert -w 48 -h 48 icons/icon.svg -o icons/icon48.png
    rsvg-convert -w 96 -h 96 icons/icon.svg -o icons/icon96.png
    echo "Icons created successfully using rsvg-convert!"
elif command -v convert &> /dev/null; then
    convert -background none -resize 16x16 icons/icon.svg icons/icon16.png
    convert -background none -resize 32x32 icons/icon.svg icons/icon32.png
    convert -background none -resize 48x48 icons/icon.svg icons/icon48.png
    convert -background none -resize 96x96 icons/icon.svg icons/icon96.png
    echo "Icons created successfully using ImageMagick!"
else
    echo "Error: Neither rsvg-convert nor ImageMagick convert command found."
    echo "Please install one of these tools:"
    echo "  Ubuntu/Debian: sudo apt-get install librsvg2-bin"
    echo "  or: sudo apt-get install imagemagick"
    echo "  macOS: brew install librsvg"
    echo "  or: brew install imagemagick"
    exit 1
fi
