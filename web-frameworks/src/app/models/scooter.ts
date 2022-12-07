export class Scooter {

  constructor(public id: number,
              public tag: string,
              public status: ScooterStatus,
              public gpsLocation: string,
              public mileage: number,
              public batteryCharge: number
  ) {
  }

  static createCopy(scooter: Scooter): Scooter {
    return new Scooter(scooter.id, scooter.tag, scooter.status, scooter.gpsLocation, scooter.mileage, scooter.batteryCharge);
  }

  static trueCopy(scooter: Scooter): Scooter {
    return scooter == null ? null : Object.assign(new Scooter(scooter.id, scooter.tag, scooter.status,
      scooter.gpsLocation, scooter.mileage, scooter.batteryCharge), scooter);
  }

  public static createRandomScooter(): Scooter {
    return new Scooter(createUniqueId(),
      Math.random().toString(36).substr(2, 8),
      getRandomStatus(),
      '52.369485, 4.846955',
      createRandomNumber(200),
      5 + createRandomNumber(100));
  }

  setId(id: number): void {
    this.id = id;
  }
}

/*
Creates a random ID
 */
let highestId = 3001;
const createUniqueId = (): number => {
  highestId++;
  return highestId;
};

/*
Creates a random status for a scooter object
*/
const getRandomStatus = (): ScooterStatus => {
  const result = createRandomNumber(3);
  if (result === 0) {
    return ScooterStatus.IDLE;
  } else if (result === 1) {
    return ScooterStatus.INUSE;
  } else {
    return ScooterStatus.MAINTENANCE;
  }
};

enum ScooterStatus {
  IDLE = 'IDLE',
  INUSE = 'IN USE',
  MAINTENANCE = 'MAINTENANCE',
}

/*
Creates a random number for the MileAge and BatteryCharge using a multiplier and makes sure its rounded
 */
const createRandomNumber = (multiply: number): number => {
  return Math.floor(Math.random() * multiply);
};

