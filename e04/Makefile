CC=gcc
CC_OPT=-m32

all: a.out

a.out: orig.s
	${CC} ${CC_OPT} orig.s

orig.s: source.s
	cp source.s orig.s

out.s: a.out
	./a.out > out.s

test: orig.s out.s
	diff orig.s out.s

clean:
	rm a.out
	rm out.s
