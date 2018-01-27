#!/bin/env python3

import sys


def search(haystack, needle):
    count = 0
    nl = len(needle)
    needle = needle.lower()
    for i in range(len(haystack)-nl+1):
        if haystack[i:i+nl].lower() == needle:
            count += 1
    print('\n\nfound "' + needle + '" ' + str(count) + 'x')
    # print(haystack.lower().count(needle.lower()))
    return count


if __name__ == "__main__":
    # execute only if run as a script
    if len(sys.argv) < 3:
        print("Usage: " + sys.argv[0] + " <file> <word>")
        exit(1)

    with open(sys.argv[1]) as f:
        search(f.read(), sys.argv[2])
