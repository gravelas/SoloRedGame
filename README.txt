HW03

1. Changed the field "int num" and "Color color" in SoloCard.java to be final so that the card class is immutable.
   a. Changing Color color to be final involved a restructuring of how color is assigned,
      instead of being in the subclass, it is now done in the parent class through the super call.

2. Made the SoloCard constructor check if the Color is null so that cards are always valid.

3. Made all the fields in SoloRedGameModel private to further restrict visibility of the class.

4. Added some documentation to SoloRedGameModel, with the Lists to specify indices and
   for start game to specify null deck behavior.

5. Rewrote CardBuilder to be a swtich statement for better readability.

6. Added purpose statement to biggestOrRedCardWinner in SoloCard.

7. Added purpose statements to shuffle and dealFromDeck in SoloRedGameModel.


HW04

1. In SoloRedGameModel, I changed how drawing works in the beginning, as my drawForHand() method changed from draw till full to draw once/twice.