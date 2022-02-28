from tokenize import Triple


w = open("./memory.log", "w")
write = False
with open("./cleaned.log", "r") as f:
    lines = f.readlines()
    for line in lines:
        if "hs_gc" in line:
            w.write(line)

w.close()