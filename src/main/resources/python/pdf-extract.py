import pdfplumber
import json
from enum import Enum
import sys
import re

header_chung = {
    'teacherName': 'Họ và tên',
    'courseId': 'Mã MH',
    'courseName': 'Tên môn học',
    'courseGroup': 'NH',
    'amount': 'Số SV',
}

header_day = {
    'dayOfWeek': 'Thứ',
    'periodStart': 'Tiết\nBĐ',
    'periodTotal': 'Số tiết'
}



header_day_no_valid_chung = {
    'practiceGroup': 'Tổ TH',
    'room': 'Phòng',
    'totalWeek': 'Thời gian học'
}

header_day_no_valid_chuyen = {
    'practiceGroup': 'Tổ TH',
    'room': 'Mã phòng',
    'totalWeek': 'Tuần học'
}

header_chuyen = {
    'teacherName': 'Họ và tên',
    'courseId': 'Mã MH',
    'courseName': 'Tên môn học',
    'courseGroup': 'Nhóm',
    'amount': 'Sỉ số',
}



# disable print error for faster run
# def print(*props):
#     pass

args = sys.argv

try:
    pdf_path = args[1]
    pdf_type = args[2]
    year_period = int(args[3])
    year_from = int(args[4])
    year_to = int(args[5])
    if (year_from > year_to):
        raise Exception('Year from must less than year to')
    if (year_period < 1 or year_period > 3):
        raise Exception('Year period must be 1, 2 or 3')
except Exception as e:
    print('Argument invalid:', e)
    exit()


print(pdf_path, pdf_type)

if (pdf_type != 'chung' and pdf_type != 'chuyen'):
    print('Argument pdf_type invalid')
    exit()

if (pdf_type == 'chuyen'):
    try:
        chuyen_nganh = args[6]
    except Exception as e:
        print('Argument chuyen_nganh invalid:', e)
        exit()

# if pdf_type == 'chung' or pdf_type == 'chuyen':
#     exit()


with pdfplumber.open(pdf_path) as pdf:

    header = pdf.pages[0].extract_table()[0]

    # # Get year and period of scheduler
    # for page in pdf.pages:
    #     print(page.lines)
    #     exit()
    #     for line in page.lines:

    #         # if line.get

    #         result = re.findall(r'(?<=Học kỳ )\d', text)
    #         try:
    #             period = int(result[0])
                
    #         except Exception as e:
    #             # print('Get period error', e)
    #             continue

    #         result = re.findall(r'(?<=Năm học )\d{4}', text)

    # if not period:
    #     print('Period and year not found')
    #     exit()
    
    # exit()
            
    # print(header)

    # Header select by type of pdf
    header_select = { 
        'chung': header_chung,
        'chuyen': header_chuyen
    }
    header_day_no_valid = {
        'chung': header_day_no_valid_chung,
        'chuyen': header_day_no_valid_chuyen
    }

    # map key header with header of file
    map = {}
    map_day = {}
    map_th = {}
    count = 0

    try:
        # Get index of course in file header
        for key, value in header_select[pdf_type].items():
            map[key] = header.index(value)
        # print(map)
        
    except Exception as e:
        print('Get course header error:', e)
        exit()


    try:
        # Get index of day in file header
        for key, value in header_day.items():
            map_day[key] = header.index(value)
            
    except Exception as e:
        print('Get day header error:', e)
        exit()

    try:
        # Get index of day in file header
        for key, value in header_day_no_valid[pdf_type].items():
            map_th[key] = header.index(value)
            
    except Exception as e:
        print('Get day no valid header error:', e)
        exit()

        


    schedule = []
    for page_num, page in enumerate(pdf.pages):
        days = []
        course = {}

        tables = page.extract_table()
        for row_num, row in enumerate(tables):
            day = {}
            added = False
            try:
                # Check valid line
                for key, value in map_day.items():
                    if row[value] == None:
                        raise Exception('Invalid row:')
                    day[key] = int(row[value])
                
                for key, value in map_th.items():
                    day[key] = row[value]
                # print(day)
                count+=1



            except Exception as e:
                print(f'Get day of row ${row_num} in ${page_num} error:', e)
                continue

            
            row_info = {}
            try: 
                for key, value in map.items():
                    row_info[key] = row[value]
                
            except Exception as e:
                print(f'Get course of row ${row_num} in ${page_num} error:', e)
                continue


            
            if not course:
                course = row_info
                course['days'] = []
            
            if row_info['courseGroup'] != None and (row_info['courseGroup'] != course['courseGroup'] or row_info['courseId'] != course['courseId']):
                schedule.append(course)
                added = True
                course = row_info
                course['days'] = []

            course['days'].append(day)
            # count+=1

            # Fix last row not add in pdf (chung type)
            if row_num == len(tables) - 1 and not added:
                schedule.append(course)
            


            # if row[9] and row[10] and row[11]:
                
            #     # Check new course or old
            #     if not course:
            #         for key, value in map.items():
            #             course[key] = row[value]
            #     else:
                   
        # print(schedule)
                
        # break
    if (pdf_type == 'chuyen'):
        pdf_type = chuyen_nganh
    json.dump({
        'scheduler': schedule,
        # 'year' : {
            
        # }
        }, open(f'schedule_{pdf_type}_{year_period}_{year_from}_{year_to}.json', 'w', encoding="utf-8"), ensure_ascii=False)
    print("Total row added", count)