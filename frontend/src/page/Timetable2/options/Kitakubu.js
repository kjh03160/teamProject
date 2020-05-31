class Kitakubu {
  constructor(lectureForms, lectures, credit) {
    this.lectureForms = lectureForms;
    this.lectures = lectures;
    this.credit = credit;
    this.displayLectures = {};
  }

  // TODO: 강의 배치 로직 구현
  execute() {
    this.lectureForms.forEach((lectureForm) => {
      const lecture = this.lectures[lectureForm];

      if (lecture.time) {
        const times = lecture.time.split(',');

        times.forEach((time) => {
          const weekday = time.replace(/\s/g, '').split('')[0];
          const hours = time.replace(/\s/g, '').split('')[1].toUpperCase();
          console.log(111111+weekday)
          console.log(2222222+hours)
          if (weekday && hours) {
            const key = `${weekday}${hours}`;

            this.displayLectures = {
              ...this.displayLectures,
              [key]: {
                name: lecture.name,
                professor: lecture.professor,
                location: lecture.location,
                isRequired: lecture.isRequired,
                weekday,
                hours
              }
            };
          }
        });
      }
    });

    return this.displayLectures;
  }
}

export default Kitakubu;
