| commit                                  | time      |
|-----------------------------------------|-----------|
| implemented four of a kind              | ±30 mins  |
| implement straight flush                | ±20 mins  |
| implemented comparison of royal flushes | ±120 mins |
| implemented `Hand` parsing              | ±30 mins  |
| implemented `Card` parsing              | ±40 mins  |
| init                                    | ±30 mins  |

**`TOTAL HOURS`**:
`awk -F '|' '{print $3}' TIME_TRACKING.md | perl -lne 'print for /(\d+)/' | awk 'NF{sum+=$1} END {print sum}' | awk '{printf "%.1f Hours", ($1 / 60)}'`
