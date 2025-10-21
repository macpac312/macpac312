#!/usr/bin/env python3
"""
Create simple placeholder PNG icons for the Firefox extension.
These are basic solid color icons that will work for testing.
For better looking icons, use the SVG file with a proper converter.
"""

import struct
import zlib

def create_png(width, height, color_rgb):
    """Create a simple PNG file with a solid color."""

    def make_chunk(chunk_type, data):
        """Create a PNG chunk."""
        chunk = chunk_type + data
        crc = zlib.crc32(chunk) & 0xffffffff
        return struct.pack('>I', len(data)) + chunk + struct.pack('>I', crc)

    # PNG signature
    png = b'\x89PNG\r\n\x1a\n'

    # IHDR chunk
    ihdr = struct.pack('>IIBBBBB', width, height, 8, 2, 0, 0, 0)
    png += make_chunk(b'IHDR', ihdr)

    # IDAT chunk (image data)
    raw_data = b''
    for y in range(height):
        raw_data += b'\x00'  # Filter type
        for x in range(width):
            raw_data += bytes(color_rgb)  # RGB pixels

    compressed_data = zlib.compress(raw_data, 9)
    png += make_chunk(b'IDAT', compressed_data)

    # IEND chunk
    png += make_chunk(b'IEND', b'')

    return png

# Create icons in different sizes
# Using purple color to match the extension theme (hex: #667eea)
purple = (102, 126, 234)

sizes = [16, 32, 48, 96]

for size in sizes:
    png_data = create_png(size, size, purple)
    with open(f'icons/icon{size}.png', 'wb') as f:
        f.write(png_data)
    print(f'Created icons/icon{size}.png ({size}x{size})')

print('\nPlaceholder icons created successfully!')
print('For better icons, use the create-icons.sh script with proper tools.')
