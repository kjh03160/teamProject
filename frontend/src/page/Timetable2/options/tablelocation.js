class Tablelocation {
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
    
            if (weekday && hours) {
              const key = `${weekday}${hours}`;
              console.log(key)
  
        
          
            }
          });
        }
      });
  
      return this.displayLectures;
    }
  }
  
  export default Tablelocation;