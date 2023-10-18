import React from 'react'

const Result = ({ isWin, handleRestart }) => {
  return (
    <div>
      <p className="main__result-text">
        {isWin ? 'You win!' : 'Sorry, you lost :('}
      </p>
      <button className="main__restart" onClick={handleRestart}>Try again
      </button>
    </div>
  )
}

export default Result
