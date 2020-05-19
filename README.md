# PKLB
Custom Plugin Project Korra LeaderBoard Plugin commissioned by chrismwiggs#4011
PlaceHolders:
%pklb_totallevel% -> for showing total level of a player
%pklb_levelleaderboard% -> for showing the leaderboard of the total level of people on the server

Commands:
/lb level -> shows total level leaderboard of people on the server
/lb level <AbilityName> -> shows ability level leaderboard for the specified ability of the people on the server (Case Sensitive)
/lb uses <AbilityName> -> shows ability uses leaderboard for the specified ability of the people on the server (Case Sensitive)
  
Configuration:
activetime: (Long value) -> the amount of time the server considers a player to be active from his last logon time (put negative for forever active)
refreshtime: (Long value) -> the amount of time the server chills out before refreshing the leaderboard.
