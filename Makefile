PROJECT?=toplevel
ELABORATE?=DMAController.DMADriver
TB=DMAController.ControllerSpec

SIZE=512
SIZE_HALF=256
IMG=bunny.png

TAG:=$(shell git describe --tags --abbrev=0)
export TAG

export DMACONFIG=AXIS_AXIL_AXI

BUILD_DIR = ./build
SIM_DIR = ./sim

verilog:
	mill __.runMain $(ELABORATE) -td $(BUILD_DIR)

test:
	convert -resize $(SIZE_HALF)x$(SIZE_HALF) $(IMG) img0.rgba
	convert -resize $(SIZE)x$(SIZE) $(IMG) img1.rgba
	# $(SBT) "testOnly -t *$(TB)"
	mill -i __.test.runMain $(TB) -td $(SIM_DIR)
	convert -size $(SIZE)x$(SIZE) -depth 8 out.rgba out.png

help:
	mill -i __.test.runMain $(ELABORATE) --help

clean:
	mill -i clean
	-rm -rf out
	-rm -rf $(SIM_DIR)

distclean: clean
	-rm -rf $(BUILD_DIR)
