Equivalent Exchange 4

EMC values can be set via datapack with the path equivalentexchange/ee_emc/*.json

Putting "#" in-front of the value will result in it being used as an itemtag

eg:
```
{
  "values": {
    "#forge:seeds/wheat": 16,
    "#forge:seeds/beetroot": 16,
    "#forge:crops/wheat": 24,
    
    "minecraft:cobblestone": 1,
    "minecraft:granite": 16,
    "minecraft:diorite": 16
  }
}
```
A tag of equivalentexchange/emcdenylist can be added to items to stop emc values being given an emc value via recipes

eg:

```
{
  "replace": false,
  "values": [
    "minecraft:bedrock",
    "minecraft:coal_ore"
  ]
}
```


***Events***

//This is called before any values are set via recipes

EmcRegisterEvent.PreEvent()

//This is called after all other values are set

EmcRegisterEvent.PreEvent()

***Project E Compat***

Turning on the config for PROJECT_E_COMPAT will allow EE4 to attempt to load Project E compat from other mods.

(This is a WIP and might cause some strange values)
