import pdfplumber
import json

header_chung = {
    'gv': 'Họ và tên',
    'mmh': 'Mã MH',
    'tmh': 'Tên môn học',
    'nmh': 'NH',
    'so_luong': 'Số SV',
}

header_day = {
    'thu': 'Thứ',
    'tbd': 'Tiết\nBĐ',
    'st': 'Số tiết'
}



header_day_no_valid_chung = {
    'tth': 'Tổ TH',
    'lop': 'Phòng',
    'tuan': 'Thời gian học'
}

header_day_no_valid_chuyen = {
    'tth': 'Tổ TH',
    'lop': 'Mã phòng',
    'tuan': 'Tuần học'
}

header_chuyen = {
    'gv': 'Họ và tên',
    'mmh': 'Mã MH',
    'tmh': 'Tên môn học',
    'nmh': 'Nhóm',
    'so_luong': 'Sỉ số',
}

# disable print error for faster run
def print(*props):
    pass

with pdfplumber.open("DKP-HK2-2022-2023.pdf") as pdf:

    header = pdf.pages[0].extract_table()[0]
    # print(header)

    # Header select by type of pdf
    header_select = header_chuyen

    # map key header with header of file
    map = {}
    map_day = {}
    map_th = {}


    try:
        # Get index of course in file header
        for key, value in header_select.items():
            map[key] = header.index(value)
        # print(map)
        
    except Exception as e:
        print('Get course header error', e)
        exit()


    try:
        # Get index of day in file header
        for key, value in header_day.items():
            map_day[key] = header.index(value)
            
    except Exception as e:
        print('Get day header error', e)
        exit()

    try:
        # Get index of day in file header
        for key, value in header_day_no_valid_chuyen.items():
            map_th[key] = header.index(value)
            
    except Exception as e:
        print('Get day no valid header error', e)
        exit()

        
    schedule = []


    for page_num, page in enumerate(pdf.pages):
        days = []
        course = {}

        for row_num, row in enumerate(page.extract_table()):
            day = {}
            try:
                # Check valid line
                for key, value in map_day.items():
                    if row[value] == None:
                        raise Exception('Invalid row')
                    day[key] = int(row[value])
                
                for key, value in map_th.items():
                    day[key] = row[value]
                # print(day)



            except Exception as e:
                print(f'Get day of row ${row_num} in ${page_num} error', e)
                continue

            
            row_info = {}
            try: 
                for key, value in map.items():
                    row_info[key] = row[value]
                
            except Exception as e:
                print(f'Get course of row ${row_num} in ${page_num} error', e)
                continue


            
            if not course:
                course = row_info
                course['days'] = []
            
            if row_info['nmh'] != None and (row_info['mmh'] != course['mmh'] or row_info['mmh'] != course['mmh']):
                schedule.append(course)
                course = row_info
                course['days'] = []
            
                
                
                

            course['days'].append(day)
                
            


            # if row[9] and row[10] and row[11]:
                
            #     # Check new course or old
            #     if not course:
            #         for key, value in map.items():
            #             course[key] = row[value]
            #     else:
                   
        # print(schedule)
                
        # break
        json.dump(schedule, open('schedule.json', 'w'), ensure_ascii=False)