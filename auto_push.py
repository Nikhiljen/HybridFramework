import subprocess
import os
from datetime import datetime

# Change to your repository directory
repo_path = r"C:\HybridFramework\HybridFramework"

# Commit message with timestamp
commit_message = f"Auto commit on {datetime.now().strftime('%Y-%m-%d %H:%M:%S')}"

# Commands to run
commands = [
    "git add .",
    f'git commit -m "{commit_message}"',
    "git push"
]

# Change directory to the repository
os.chdir(repo_path)

# Run each command
for cmd in commands:
    process = subprocess.run(cmd, shell=True, text=True, capture_output=True)
    print(f"Running: {cmd}")
    print(process.stdout)
    if process.stderr.strip():
        print("Error:", process.stderr)
