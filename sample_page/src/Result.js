import React from 'react'
import './Result.css'

function Result (props) {
  return (
    <div className="result">
      <pre>
        {props.result.json ?
          JSON.stringify(props.result.json, undefined, 2)
          : props.result.err}</pre>
    </div>
  )
}

export default Result
