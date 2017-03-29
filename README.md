# Test Assignments

Java program that calculate the sum of values stored in a user-specified binary file. The sum is limited to 64-bit precision.

## Input Specification

The program provided only one argument, the path to the binary file containing the list of integers. The binary file consists of an array of little-endian 32-bit unsigned integers. Malformed input is not possible and not handled.

## Output Specification

The output of the program is a single line consisting of a human-readable base-ten form of the sum of values provided in the input. The sum supports only 64-bit bits of precision.

    Example usage from /out/production/test-assignments folder:
    ,----------------------------------------,
    | shell$ sh ./sum ./simple.txt           |
    | 15                                     |
    | shell$ sh ./sum ./1_000.txt            |
    | 499500                                 |
    '----------------------------------------'

## Additional Constraints

The program must be able to handle input files consisting of more than one billion integers. Please make an effort to make your sum program perform well for larger inputs.