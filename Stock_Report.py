import yfinance as yf, pandas as pd, shutil, os, time, glob, smtplib, ssl
from get_all_tickers import get_tickers as gt
import requests
import json
import schedule
import time
from email.message import EmailMessage

tickers =  [
    "ABF.L",
    "LLOY.L",
  "AMZN",
  "MULN",
 "AAPL",
  "META",
  "INTC",
  "AMD",
 "TSLA",
  "KAL",
  "NVDA",
  "CORZ",
  "GOOGL",
  "DBGI",
  "AGLE",
  "MSFT",
  "GOOG",
  "CMCSA",
  "SOFI",
  "GILD",
  "AAL",
  "COMS",
  "WISH",
  "HAAC",
  "AGNC",
  "ZVO",
  "MU",
  "WBD",
  "EPIX",
  "CSCO",
  "MARA",
  "FFIE",
  "TCDA",
  "AGFY",
  "PLUG",
  "PYPL"
  ]
  
def analysis():
    print("The amount of stocks chosen to observe: " + str(len(tickers)))

    shutil.rmtree("C:\\Users\\Admin\\Downloads\\Daily_Stock_Report-master\\Daily_Stock_Report-master\\Stocks\\")
    os.mkdir("C:\\Users\\Admin\\Downloads\\Daily_Stock_Report-master\\Daily_Stock_Report-master\\Stocks\\")

    Stock_Failure = 0
    Stocks_Not_Imported = 0


    i=0
    while (i < len(tickers)): 
        try:
            stock = tickers[i]  # Gets the current stock ticker
            temp = yf.Ticker(str(stock))
            Hist_data = temp.history(period="1mo")  
            Hist_data.to_csv("C:\\Users\\Admin\\Downloads\\Daily_Stock_Report-master\\Daily_Stock_Report-master\\Stocks\\"+stock+".csv") 
            time.sleep(1)
            Stock_Failure = 0
            i += 1  
        except ValueError:
            print("Yahoo Finance Error")
            if Stock_Failure > 5:
                i+=1
                Stocks_Not_Imported += 1
            Stock_Failure += 1
    print("The amount of stocks we successfully imported: " + str(i - Stocks_Not_Imported))

    ################# OBV Analysis ################
    list_files = (glob.glob("C:\\Users\\Admin\\Downloads\\Daily_Stock_Report-master\\Daily_Stock_Report-master\\Stocks\\*.csv")) # Creates a list of all csv filenames in the stocks folder
    new_data = [] #   OBV score
    interval = 0  
    while interval < len(list_files):
        Data = pd.read_csv(list_files[interval]).tail(10)  # Gets the last 10 days of trading for the current stock in iteration
        pos_move = []  # List of days that the stock price increased
        neg_move = []  # List of days that the stock price increased
        OBV_Value = 0  
        count = 0
        while (count < 10):  # last 10 trading days
            if Data.iloc[count,1] < Data.iloc[count,4]:  # True if the stock increased in price
                pos_move.append(count)  # Add the day to the pos_move list
            elif Data.iloc[count,1] > Data.iloc[count,4]:  # True if the stock decreased in price
                neg_move.append(count)  # Add the day to the neg_move list
            count += 1
        for i in pos_move:  # Adds the volumes of positive days to OBV_Value, divide by opening price to normalize across all stocks
            OBV_Value = round(OBV_Value + (Data.iloc[i,5]/Data.iloc[i,1]))
        for i in neg_move:  # Subtracts the volumes of negative days from OBV_Value, divide by opening price to normalize across all stocks
            OBV_Value = round(OBV_Value - (Data.iloc[i,5]/Data.iloc[i,1]))
        Stock_Name = ((os.path.basename(list_files[interval])).split(".csv")[0])  # Get the name of the current stock we are analyzing
        new_data.append([Stock_Name, OBV_Value])  # Add the stock name and OBV value to the new_data list
        interval += 1
    df = pd.DataFrame(new_data, columns = ['Stock', 'OBV_Value'])  # Creates a new dataframe from the new_data list
    df["Stocks_Ranked"] = df["OBV_Value"].rank(ascending = False)  # Rank the stocks by their OBV_Values
    df.sort_values("OBV_Value", inplace = True, ascending = False)  # Sort the ranked stocks
    df.to_csv("C:\\Users\\Admin\\Downloads\\Daily_Stock_Report-master\\Daily_Stock_Report-master\\OBV_Ranked.csv", index = False)  # Save the dataframe to a csv
    # OBV_Ranked.csv contains the ranked stocks


    


    ########## send mail ###############
    Analysis = pd.read_csv("C:\\Users\\Admin\\Downloads\\Daily_Stock_Report-master\\Daily_Stock_Report-master\\OBV_Ranked.csv")  # Read in the ranked stocks

    top10 = Analysis.head(10)  
    bottom10 = Analysis.tail(10)  

    
    Body_of_Email = """From: JMlessous <oussama.chaabou@gmail.com>
    Subject: Rapport quotidien sur les actions

    Actions avec l OBV le mieux classe du jour:

    """ + top10.to_string(index=False) + """\


    Actions avec l OBV le moins classe du jour:

    """ + bottom10.to_string(index=False) + """\


    Cordialement,
    JMlessous"""
    r = requests.get('http://localhost:8082/banque-en-ligne/action/emails')

    context = ssl.create_default_context()
    
    
    
    Email_Port = 465  
    with smtplib.SMTP_SSL("smtp.gmail.com", Email_Port, context=context) as server:
        server.login("oussama.chaabou@gmail.com", "bltalseyxrjghzmq")
        server.sendmail("oussama.chaabou@gmail.com", r.json(), Body_of_Email)
        


 
schedule.every().day.at("14:07").do(analysis)
while 1:
    schedule.run_pending()
    time.sleep(1)
