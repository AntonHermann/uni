#!/bin/env python3

import sys


def search(haystack, needle):
    haystack = haystack.lower()
    needle = needle.lower()
    nl = len(needle)
    hneedle = 0
    for c in needle:
        hneedle += ord(c)
    count = 0
    h = 0
    for c in haystack[:nl-1]:
        h += ord(c)

    for i in range(len(haystack)-nl):
        h += ord(haystack[i+nl-1])
        if i > 0:
            h -= ord(haystack[i-1])

        if h == hneedle:
            if haystack[i:i+nl] == needle:
                count += 1
    print('\n\nfound "' + needle + '" ' + str(count) + 'x')
    return count


if __name__ == "__main__":
    # execute only if run as a script
    if len(sys.argv) < 3:
        print("Usage: " + sys.argv[0] + " <file> <word>")
        exit(1)

    with open(sys.argv[1]) as f:
        search(f.read(), sys.argv[2])
