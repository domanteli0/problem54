| commit                                  | time                  |
|-----------------------------------------|-----------------------|
| fix most linter warnings                | ±25 mins              |
| more refactoring                        | ±110 mins             |
| refactoring                             | ±20 mins              |
| add main                                | ±30 mins              |
| implemented rest of the rules           | ±120 mins             |
| implement 3-of-a-kind                   | ±20 mins              |
| implemented straights                   | ±30 mins              |
| implemented flushes                     | ±70 mins              |
| refactoring                             | ±60 mins (inaccurate) |
| implemented full house                  | ±30 mins              |
| add tests for fullhouse + fix bug       | ±25 mins              |
| implemented four of a kind              | ±30 mins              |
| implement straight flush                | ±20 mins              |
| implemented comparison of royal flushes | ±120 mins             |
| implemented `Hand` parsing              | ±30 mins              |
| implemented `Card` parsing              | ±40 mins              |
| init                                    | ±30 mins              |

**`TOTAL HOURS`**:
`awk -F '|' '{print $3}' TIME_TRACKING.md | perl -lne 'print for /(\d+)/' | awk 'NF{sum+=$1} END {print sum}' | awk '{printf "%.1f Hours", ($1 / 60)}'`
