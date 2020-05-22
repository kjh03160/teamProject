import React from 'react';
import Button from '@material-ui/core/Button';
import Radio from '@material-ui/core/Radio';
import RadioGroup from '@material-ui/core/RadioGroup';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import FormControl from '@material-ui/core/FormControl';
import TimeBlock from './TimeBlock';
import Normal from '../options/Normal';
import Kitakubu from '../options/Kitakubu';
import jammanbo from '../options/jammanbo'


class Timetable extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      option: 'normal',
      weekday: ['월', '화', '수', '목', '금', '토', '일'],
      timeUnitAlphabet: ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M'],
      timeUnitString: [
        'A 9:00 ~ 9:50',
        'B 10:00 ~ 10:50',
        'C 11:00 ~ 11:50',
        'D 12:00 ~ 12:50',
        'E 13:00 ~ 13:50',
        'F 14:00 ~ 14:50',
        'G 15:00 ~ 15:50',
        'H 16:00 ~ 16:50',
        'I 17:00 ~ 17:50',
        'J 18:00 ~ 18:50',
        'K 19:00 ~ 19:50',
        'L 20:00 ~ 20:50',
        'M 21:00 ~ 21:50'
      ],
      displayLectures: {}
    };
  }


//  handleClick = (w, t) => {
//   console.log(w)
//   console.log(t)
//     w=this.getWeekdayIndex(w)
//     t=this.getTimeIndex(t)
//     console.log(w)
//     console.log(t)

//     const { weekday } = this.state;
//     const {timeUnitAlphabet} =this.state;

    // 파라미터로 받은 id 를 가지고 몇번째 아이템인지 찾습니다.
    //const i = weekday.findIndex(weekday=>weekday[w]===weekday);
    //const j=  timeUnitAlphabet.findIndex();
    //const index = todos.findIndex(todo => todo.id === id);
    //const selected = todos[index]; // 선택한 객체

    // const nextTodos = [...todos]; // 배열을 복사

    // // 기존의 값들을 복사하고, checked 값을 덮어쓰기
    // nextTodos[index] = { 
    //   ...selected, 
    //   checked: !selected.checked,
      
    //  // text: todos.checked? "공강": "공강x"
    //  text:"  "
    //   //text: todos.text==="공강"? " - " :"공강"  
    //   , background: 'gray'
    // };
    
    
    // this.setState({
    //   todos: nextTodos
    // });
  //}

  handleClick=()=>{
    console.log(this.id)
  }

  setOption = (e) => {
    this.setState({
      option: e.target.value
    });
  }

  getWeekdayIndex(target) {
    const { weekday } = this.state;

    for (let i = 0; i < weekday.length; i += 1) {
      if (target === weekday[i]) {
        return i;
      }
    }

    return -1;
  }

  getTimeIndex(target) {
    const { timeUnitAlphabet } = this.state;

    for (let i = 0; i < timeUnitAlphabet.length; i += 1) {
      if (target === timeUnitAlphabet[i]) {
        return i;
      }
    }

    return -1;
  }

  setTable = () => {
    const { credit, lectureForms, lectures } = this.props;
    const { option } = this.state;

    let computedLectures = null;

    this.setState({ displayLectures: {} });

    // TODO: option 값에 따라 인스턴스 생성하도록 개선.
    if (option === 'normal') {
      computedLectures = new Normal(lectureForms, lectures).execute();
    } else if (option === 'kitakubu') {
      computedLectures = new Kitakubu(lectureForms, lectures, credit).execute();
    }
    else if (option === 'jammanbo') {
      computedLectures = new jammanbo(lectureForms, lectures, credit).execute();
    }


    this.setState({
      displayLectures: computedLectures
    });
  }

  render() {
    console.log("sts", this.state)
    const {
      option,
      weekday,
      timeUnitAlphabet,
      timeUnitString,
      displayLectures
    } = this.state;

    return (
      <div id="timetable">
        <div id="timetable-radios">
          <FormControl component="fieldset" required className="option-radio-form">
            <RadioGroup
              aria-label="옵션 설정"
              name="option-radio"
              value={option}
              onChange={this.setOption}
              className="option-radio-form"
            >
              <FormControlLabel value="normal" control={<Radio />} label="일반" />
              <FormControlLabel value="kitakubu" control={<Radio />} label="아침형" />
              <FormControlLabel value="jammanbo" control={<Radio />} label="오후형" />
             
            </RadioGroup>
          </FormControl>
        </div>

        <Button variant="contained" color="primary" onClick={this.setTable}>
          {'적용하기!'}
        </Button>





        <table onClick={()=>this.handleClick(weekday[5], timeUnitAlphabet[3])} >
          <thead>
            <tr>
              <th>
                {'시간'}
              </th>
              {weekday.map((w) => {
                return (
                  <th key={w}>
                    {w}
                  </th>
                );
              })}
            </tr>
          </thead>
          <tbody onClick={()=>this.handleClick(this.props)}>
            {timeUnitString.map((time, t) => {
              
              return (
                <tr key={time} id={time.split('')[0]} className="block">
                  <td className="block-time">
                    {time}
                  </td>
                  
                  {Array.from(Array(7).keys()).map((w) => {
                    const displayLectureKey = `${weekday[w]}${timeUnitAlphabet[t]}`;
                      console.log(w)
                      console.log(t)
                      
                    return (
                      <TimeBlock
                        key={w} 
                        displayLecture={displayLectures[displayLectureKey]
                         }
                      />);
                  })}
                </tr>
              );
            })}
          </tbody>
        </table>
      </div>
    );
  }
}

export default Timetable;
