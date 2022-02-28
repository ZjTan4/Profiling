from tokenize import Triple


w = open("./threads.log", "w")
write = False
with open("./cleaned.log", "r") as f:
    lines = f.readlines()
    for line in lines:
        if "threads.alive" in line:
            w.write(line)

w.close()