Feature: Trades are added correctly

  Scenario: Trade addes should be same from all daos
    Given Stocks Existing 
    When a trade is added in each stock
    Then sum of trades in all stocks should be same as sum of trades
