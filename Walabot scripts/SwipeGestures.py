from __future__ import print_function, division
from math import radians, sin
import WalabotAPI
try:
    input = raw_input
except NameError:
    pass


class Walabot:
    """ Designed to control the Walabot device.
    """

    def __init__(self):
        """ Set useful arena constants and load the Walabot SDK.
        """
        self.wlbt = WalabotAPI
        self.wlbt.Init()
        self.wlbt.SetSettingsFolder()
        self.R_MIN, self.R_MAX, self.R_RES = 5, 30, 1
        self.THETA_MIN, self.THETA_MAX, self.THETA_RES = -0, 20, 10
        self.PHI_MIN, self.PHI_MAX, self.PHI_RES = -30, 30, 1
        self.TSHLD = 40
        self.Y_MAX = self.R_MAX * sin(radians(self.PHI_MAX))
        self.PAN = self.Y_MAX / 2
        self.MIDDLE = self.R_MAX * 7 / 8
        self.distance = lambda t: (t.xPosCm**2+t.yPosCm**2+t.zPosCm**2) ** 0.5

    def connect(self):
        """ Connect to a Walabot device. Prompt the user to connect one if
            it can't detect it.
        """
        while True:
            try:
                self.wlbt.ConnectAny()
            except self.wlbt.WalabotError as err:
                if err.code == 19:  # 'WALABOT_INSTRUMENT_NOT_FOUND'
                    input("- Connect Walabot and press 'Enter'.")
            else:
                print('- Connection to Walabot established.')
                return

    def setParametersAndStart(self):
        """ Set the Walabot required parameters according to the constants.
            Then start the Walabot.
        """
        self.wlbt.SetProfile(self.wlbt.PROF_SENSOR)
        self.wlbt.SetArenaR(self.R_MIN, self.R_MAX, self.R_RES)
        self.wlbt.SetArenaTheta(self.THETA_MIN, self.THETA_MAX, self.THETA_RES)
        self.wlbt.SetArenaPhi(self.PHI_MIN, self.PHI_MAX, self.PHI_RES)
        self.wlbt.SetThreshold(self.TSHLD)
        self.wlbt.SetDynamicImageFilter(self.wlbt.FILTER_TYPE_MTI)
        self.wlbt.Start()
        print('- Walabot started.')

    def getClosestTarget(self):
        """ Trigger the Walabot and retrieve SensorTargets. Return the closest.
            Return:
                targets     Of SensorTarget type (from WalabotAPI).
        """
        self.wlbt.Trigger()
        targets = self.wlbt.GetSensorTargets()
        try:
            return max(targets, key=self.distance)
        except ValueError:  # 'targets' is empty; no targets were found
            return None

    def stopAndDisconnect(self):
        """ Stop and disconnect from the Walabot device.
        """
        self.wlbt.Stop()
        self.wlbt.Disconnect()
        print('- Walabot stopped and disconnected.')

wlbt = Walabot()


def MapTargetToHandPosition(target):
    if not target:
        #raspPi.stop()
        print("No Target found")
    elif abs(target.yPosCm) > wlbt.PAN:  
        print(" Hand is at the right")
    elif target.zPosCm < wlbt.MIDDLE:  
        print("Hand is at the left")   
    else:  
        print("Hand is at the middle")
        

def DetectGestures():

    wlbt.connect()
    wlbt.setParametersAndStart()
    try:
        while True:
            MapTargetToHandPosition(wlbt.getClosestTarget())
    finally:  
        wlbt.stopAndDisconnect()
        print('- Terminated successfully.')


if __name__ == '__main__':
    DetectGestures()
