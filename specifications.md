# Not enough java - language

## Instructions

| **Type** | **Encoding** |
|:---------|:-------------|
| Register | `op<7:5>, rs<4:2>, rt<1:0>` |
| Special  | `op<7:5>, rs<4:2>, imm<1:0>` |
| Jump     | `op<7:5>, addr<4:0>` |


## R-Instructions

| **Instruction** | **Description** |
|:----------------|:----------------|
| `add`           | `rt = rt + rs` |
| `sub`           | `rt = rt - rs`  |
| `put`           | `rt = rs` |          
| `skip`          | Skip next line if `rt == rs`|


## S-Instructions

| **Instruction** | **Description** |
|:----------------|:----------------|
| `call`          |`Call = imm`, `rs = Call-Instruct` calls a call-instruction and saves it in rs|
| `set`           | `rs = imm` |
| `counter`       | calls the specified counter instructions|

## J-Instructions

| **Instruction** | **Description** |
|:----------------|:----------------|
| `ju`             | Jump to given line or flag|


## Call-Instructions

| **Instruction** | **Description** |
|:----------------|:----------------|
| `0`             | Get integer value from input stream and store in `rs`|
| `1`            | Write the value from `rs` to the output stream|
| `2`           | Exit the program |

## Counter-Instructions

| **Instruction** | **Description** |
|:----------------|:----------------|
| `0`             | `rs = rs - %1` |
| `1`             | if `rs == %1` skip one line|
| `2`             | `rs = rs+%1`|





## Registers

    There are seven registers..

    - %0    //Always equal to zero
    - %1    //Always equal to one
    - C1    //Counter register
    - C2    //Counter register
    - R1    
    - R2                                    
    - R3
