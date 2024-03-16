| commit                     | time     |
|----------------------------|----------|
| implemented `Card` parsing | ±40 mins |
| init                       | ±30 mins |

**`TOTAL HOURS`**:
`awk -F '|' '{print $3}' TIME_TRACKING.md | perl -lne 'print for /(\d+)/' | awk 'NF{sum+=$1} END {print sum}' | awk '{printf "%.1f Hours", ($1 / 60)}'`
