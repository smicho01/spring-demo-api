#!/usr/bin/env bash

# Function to emulate initialization
initialize() {
    echo "Initializing program..."
    sleep 1  # Simulate some delay
    echo "Initialization complete!"
}

# Function to emulate processing a task
process_task() {
    echo "Processing task: $1"
    sleep $((RANDOM % 3 + 1))  # Random delay between 1-3 seconds
    echo "Task $1 completed!"
}

# Function to emulate finalization
finalize() {
    echo "Finalizing program..."
    sleep 1  # Simulate some delay
    echo "Program finalized!"
}

# Start of the program
echo "Starting program..."

# Initialize
initialize

# Emulate processing of 5 tasks
for i in {1..5}; do
    process_task "$i"
done

# Finalize
finalize

echo "Program has finished running!"