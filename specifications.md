# No error language

## Instructions

| **Type** | **Encoding** |
|:---------|:-------------|
| Register | `op<7:5>, rs<4:2>, rt<1:0>` |
| Special  | `op<7:5>, rs<4:3>, imm<2:0>` |
| Jump     | `op<7:5>, addr<4:0>` |


## R-Instructions

| **Instruction** | **Description** |
|:----------------|:----------------|
| `add`           | `rt = rt + rs + imm` |
| `sub`           | `rt = rt - rs - imm`  |
| `put`           | `rt = rs + imm` |          
| `skip`          | Skip next line if `rt == rs` or if `rt == imm`|


## S-Instructions

| **Instruction** | **Description** |
|:----------------|:----------------|
| `Call`             |`Call = imm`, `rs = Call-Instruct` calls a call-instruction and saves it in rs|
| ``               ||
| ``            | |

## Call-Instructions

| **Instruction** | **Description** |
|:----------------|:----------------|
| `1`             | Get integer value from input stream and store in `rs`|
| `2`            | Write the value from `rs` to the output stream|
| `3`           | Exit the program |

## J-Instructions

| **Instruction** | **Description** |
|:----------------|:----------------|
| `ju`             | Jump to given line|



## Registers

    There are four registers..

    - %0    //Always equal to zero
    - %1    //Always equal to one
    - R1    
    - R2                                    
    - R3
