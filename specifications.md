# No error language

## Instructions

| **Type** | **Encoding** |
|:---------|:-------------|
| Register | `op<7:6>, rs<5:4>, rt<3:2>, imm<1:0>` |
| Special  | `op<7:6>, rs<5:4>, imm<3:0>`` |
| Jump     | `op<7:6>, addr<4:0>` |


## R-Instructions

| **Instruction** | **Description** |
|:----------------|:----------------|
| `add`           | `rt = rt + rs + imm` |
| `sub`           | `rt = rt - rs - imm`  |
| `put`           | `rt = rs + imm` |           //behövs ej
| `skip`          | Jump one line if rt = rs|

## S-Instructions

| **Instruction** | **Description** |
|:----------------|:----------------|
| `in`             | Get integer value from input stream and store in `rs`|
| `out`             | Write a value to the output stream|
| `exit`             | Exit the program |

## J-Instructions

| **Instruction** | **Description** |
|:----------------|:----------------|
| `ju`             | Jump to given line|



## Registers

    There are four registers..

    - =0    //Always equal to zero
    - R1    
    - R2                                    
    - R3