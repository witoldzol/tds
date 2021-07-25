import './App.css'
import Button from './Button'
import Result from './Result'
import React, { useState } from 'react'

function App () {
  const [data, setData] = useState({ json: '', err: '' })
  const url = 'https://postman-echo.com/get?foo1=bar1&foo2=bar2'

  const handleError = err => setData(
    { json: '', err: `Oops something went wrong:  ${err.message}` })

  const fetchData = () => {
    return fetch(url)
      .then((response) => response.json())
      .catch(err => handleError())
  }

  const displayResult = data => {
    if (data.json) return <Result result={data}/>
  }

  return (
    <div className="App">
      <div onClick={() => {
        fetchData().then(data => setData({ json: data, err: '' }))
      }} className="App-header">
        <Button/>
      </div>
      <div className="App-body">
        {displayResult(data)}
      </div>
    </div>
  )
}

export default App
