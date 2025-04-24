
# Target executable name
TARGET = can_replay

# Source files
SRCS = can_replay.c

# Object files
OBJS = $(SRCS:.c=.o)

# Compiler flags: Respect CFLAGS, add warnings
CFLAGS += -Wall -Wextra -std=c99

# Linker flags: Respect LDFLAGS, add libraries
LDFLAGS += -lrt

# Define remove command
RM = rm -f

# Default target
.PHONY: all
all: $(TARGET)

# Link executable
$(TARGET): $(OBJS)
	$(CC) $(LDFLAGS) $(OBJS) -o $(TARGET)

# Compile source files
%.o: %.c
	$(CC) $(CFLAGS) -c $< -o $@

# Clean up
.PHONY: clean
clean:
	$(RM) $(TARGET) $(OBJS)
