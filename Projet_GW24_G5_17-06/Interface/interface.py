import os
from tkinter import *
from tkinter import ttk
import datetime
import time
from PIL import Image, ImageTk
 
def lire_contenu_fichier(nom_fichier):
    try:
        script_dir = os.path.dirname(__file__)  
        chemin_complet = os.path.join(script_dir, nom_fichier)
        print(f"Chemin du fichier: {chemin_complet}")
        with open(chemin_complet, 'r') as fichier:
            contenu = fichier.read().strip()
            return contenu
    except FileNotFoundError:
        return "Fichier non trouvé"
    except Exception as e:
        return str(e)
 
def lire_dernieres_lignes_fichier(nom_fichier):
    try:
        script_dir = os.path.dirname(__file__)  
        chemin_complet = os.path.join(script_dir, nom_fichier)
        print(f"Chemin du fichier: {chemin_complet}")
        with open(chemin_complet, 'r') as fichier:
            lignes = fichier.readlines()
            if len(lignes) >= 2:
                return lignes[-2].strip(), lignes[-1].strip()
            elif len(lignes) == 1:
                return lignes[0].strip(),
            else:
                return "Fichier vide", "Fichier vide"
    except FileNotFoundError:
        return "Fichier non trouvé", "Fichier non trouvé"
    except Exception as e:
        return str(e), str(e)
 
def formater_date_heure(date_heure_str):
    try:
        date_obj = datetime.datetime.strptime(date_heure_str, '%Y-%m-%d %H-%M')
        return date_obj.strftime('%d %B %Y %Hh%M')
    except ValueError:
        return date_heure_str
 
def create_alert_triangle(canvas):
    width = 100
    height = 100
    padding = 20  
    triangle_coords = [width//2, padding, padding, height-padding, width-padding, height-padding]
 
    canvas.create_polygon(triangle_coords, fill='yellow', outline='red', width=4)
 
    centroid_x = (triangle_coords[0] + triangle_coords[2] + triangle_coords[4]) // 3
    centroid_y = (triangle_coords[1] + triangle_coords[3] + triangle_coords[5]) // 3
 
    text_id = canvas.create_text(centroid_x, centroid_y, text="!", font=('Helvetica', 12, 'bold'), fill='red', justify='center')
 
    def toggle_visibility():
        current_state = canvas.itemcget(text_id, 'state')
        new_state = 'hidden' if current_state == 'normal' else 'normal'
        canvas.itemconfigure(text_id, state=new_state)
        canvas.itemconfigure(1, state=new_state)  
 
        canvas.after(500, toggle_visibility)
 
    toggle_visibility()
 
def main():
    pourcentage_txt, date_heure = lire_dernieres_lignes_fichier('../BDD/donnes/Log.txt')
    date_heure_formatee = formater_date_heure(date_heure)
    urgence = ""
 
    try:
        pourcentage = float(pourcentage_txt.replace(',', '.'))
    except ValueError:
        print(f"Erreur: '{pourcentage_txt}' ne peut pas être converti en nombre.")
        return
 
    if pourcentage > 50:
        time.sleep(5)  # Ajoute un sleep de 5 secondes
 
    else:
        fenetre = Tk()
        fenetre.title("ALERTE!")
        fenetre.geometry('700x300')
 
        if pourcentage < 30:
            urgence = "Maintenance d'urgence"
        else:
            urgence = "Anomalie détectée"
 
        top_frame = Frame(fenetre)
        top_frame.pack(fill=X, padx=10, pady=5)
 
        middle_frame = Frame(fenetre)
        middle_frame.pack(fill=BOTH, expand=True, padx=10, pady=5)
 
        bottom_frame = Frame(fenetre)
        bottom_frame.pack(fill=X, padx=10, pady=5)
 
        script_dir = os.path.dirname(__file__)
        logo_path = os.path.join(script_dir, "../BDD/LOGO.jpg")
        logo_image = Image.open(logo_path)
        logo_image = logo_image.resize((150, 100))  # Redimensionne l'image à 100x100 pixels sans spécifier de mode de lissage
        logo_photo = ImageTk.PhotoImage(logo_image)
 
        logo_label = Label(top_frame, image=logo_photo)
        logo_label.image = logo_photo  # Conserver une référence pour éviter la garbage collection
        logo_label.pack(side=RIGHT)
 
        company_label = Label(top_frame, text="Veolia", font=('Helvetica', 18, 'bold'))
        company_label.pack(side=LEFT)
 
        canvas = Canvas(middle_frame, width=100, height=100, bg='white')
        canvas.pack(side=LEFT, padx=20, pady=10)
        create_alert_triangle(canvas)
 
        precision_label = Label(middle_frame, text=f"Précision : {pourcentage}%", font=('Helvetica', 14))
        precision_label.pack(side=LEFT, padx=20, pady=10)
 
        nouvelle_ligne_label = Label(middle_frame, text=urgence, font=('Helvetica', 14))
        nouvelle_ligne_label.pack(side=LEFT, padx=20, pady=20)
 
        date_label = Label(bottom_frame, text=f"Date : {date_heure_formatee}", font=('Helvetica', 12))
        date_label.pack(anchor=CENTER)
 
        fenetre.after(5000, fenetre.quit)
 
        bouton = Button(fenetre, text="Fermer", command=fenetre.quit, font=('Helvetica', 14, 'bold'), bg='red', fg='white')
        bouton.pack(side=BOTTOM, pady=10)
 
        fenetre.mainloop()
 
if __name__ == "__main__":
    main()