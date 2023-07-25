import os
import csv

def teste():
# open the files
    with open('definicoes\TABELA_ACTION_GOTO_UNICA_BKP.csv', mode='r') as oldfile, open(
            'definicoes\TABELA_ACTION_GOTO_UNICA-CORRIGIDA.csv', mode='w', newline='') as newfile:
        # define a reader and a writer
        reader = csv.reader(oldfile, delimiter=',')
        writer = csv.writer(newfile, delimiter=',',quoting=csv.QUOTE_MINIMAL)
        # copy everything changing the third field
        linha = 1
        for row in reader:
            for i in range(len(row)):
                if (row[i] == "E"):
                    row[i] = "E"+str(linha)
                    linha = linha +1
            writer.writerow(row)

teste()