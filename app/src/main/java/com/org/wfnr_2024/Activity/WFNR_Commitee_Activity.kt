package com.org.wfnr_2024.Activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.org.wfnr_2024.Adapter.Scientific_ProgrammeAdapter
import com.org.wfnr_2024.Model.Scientific_programme_commitee_model
import com.org.wfnr_2024.R

class WFNR_Commitee_Activity:AppCompatActivity(), View.OnClickListener {


    var Scientific_programme_ArrayList:ArrayList<Scientific_programme_commitee_model>?=null

    lateinit var Event_backbtn:ImageView



    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wfnr_commitee)

        Event_backbtn=findViewById(R.id.Event_backbtn)

        Event_backbtn.setOnClickListener(this)


        Scientific_programme_ArrayList= ArrayList()

       /* Organizing_commiteeArrayList!!.add(Organizing_commitee_model("Janice Eng","Department of Physical Therapy University of British Columbia",R.drawable.janice_eng))
        Organizing_commiteeArrayList!!.add(Organizing_commitee_model("Noah Silverberg","Department of Psychology University of British Columbia",R.drawable.noah_silverberg))

        val recyclerView: RecyclerView = findViewById(R.id.RecyclerView_organizing_commitee)
        val adapter = Oraganization_Commitee_Adapter(this, Organizing_commiteeArrayList!!)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL // or HORIZONTAL
        recyclerView.layoutManager = layoutManager

        recyclerView.adapter = adapter*/

        Scientific_programme_ArrayList!!.add(Scientific_programme_commitee_model(getString(R.string.Organizing_Committee),"Janice Eng","Department of Physical Therapy University of British Columbia",R.drawable.janice_eng,""))
        Scientific_programme_ArrayList!!.add(Scientific_programme_commitee_model("","Noah Silverberg","Department of Psychology University of British Columbia",R.drawable.noah_silverberg,""))


        Scientific_programme_ArrayList!!.add(Scientific_programme_commitee_model(getString(R.string.Scientific_Programme_Committee),"Michael Barnes","Honorary Professor of Neurological Rehabilitation University of Newcastle, retired Consultant Neurologist and Consultant in Rehabilitation Medicine at the  Regional Neurorehabilitation Centre in Newcastle (Hunters Moor and latterly Walkergate Park)",R.drawable.michael_barnes,"Male"))
        Scientific_programme_ArrayList!!.add(Scientific_programme_commitee_model("","Mark Bayley","Program Medical Director & Physiatrist-in-Chief at Toronto University Health Network Rehabilitation Institute",R.drawable.mark_bayley,"Male"))
        Scientific_programme_ArrayList!!.add(Scientific_programme_commitee_model("","Lara Boyd","Professor in the Department of Physical Therapy, Faculty of Medicine at the University of British Columbia.",R.drawable.default_f_image,"Female"))
        Scientific_programme_ArrayList!!.add(Scientific_programme_commitee_model("","Iris Charlotte Brunner","Associate Professor at the Department of Clinical Medicine at Aarhus University and the Hammel Neurocenter, University Hospital for Neurorehabilitation",R.drawable.default_f_image,"Female"))
        Scientific_programme_ArrayList!!.add(Scientific_programme_commitee_model("","Robert Chen","Professor of Medicine (Neurology) at the University of Toronto, Senior Scientist at the Krembil Brain Institute, and Director of the Eliot Phillipson Clinician Scientist Training Program of the Department of Medicine at the University of Toronto",R.drawable.default_m_image,"Male"))
        Scientific_programme_ArrayList!!.add(Scientific_programme_commitee_model("","Dale Corbett","Professor (Emeritus) at the Department of Cellular and Molecular Medicine, Faculty of Medicine at the University of Ottawa",R.drawable.default_m_image,"Male"))
        Scientific_programme_ArrayList!!.add(Scientific_programme_commitee_model("","Cathy Craven","Chair in Spinal Cord Injury Rehabilitation at the Toronto Rehabilitation Institute and Professor in the Department of Medicine at the University of Toronto; Medical Director of the Spinal Cord Rehabilitation Program and Senior Scientist at KITE Research Institute within the University of Toronto Health Network",R.drawable.default_f_image,""))
        Scientific_programme_ArrayList!!.add(Scientific_programme_commitee_model("","Janice Eng","Professor, Department of Physical Therapy, Faculty of Medicine, University of British Columbia; scientist at the GF Strong Rehab Centre; Co-Congress Chair of the 13th World Congress for Neurorehabilitation",R.drawable.janice_eng,""))
        Scientific_programme_ArrayList!!.add(Scientific_programme_commitee_model("","Wayne Fang","Chief of Division of Stroke & Vascular Neurology, Medical Director of the Duke Comprehensive Stroke Center, and Tenured Profess of Neurology and Biomedical Engineering at Duke University School of Medicine",R.drawable.wayne_fang,""))
        Scientific_programme_ArrayList!!.add(Scientific_programme_commitee_model("","Marcia Finlayson","Occupational Therapist, Professor for Rehabilitation Therapy and Vice Dean (Health Sciences) at Queens University, Kingston",R.drawable.default_f_image,""))
        Scientific_programme_ArrayList!!.add(Scientific_programme_commitee_model("","David Good","Professor and Former Chair of Neurology at Penn State University, Pennsylvania, United States; Doctor at Milton S. Hershey Medical Center, Hershey, United States",R.drawable.david_good,""))
        Scientific_programme_ArrayList!!.add(Scientific_programme_commitee_model("","Jorge Hernandez Franco","Lead of the Rehabilitation Ward at the National Neurology and Neurosurgery Institute Manuel Velasco Suárez in Mexico",R.drawable.default_m_image,""))
        Scientific_programme_ArrayList!!.add(Scientific_programme_commitee_model("","Volker Hömberg","Former head of neurology and neurorehabilitation, SRH-Health Center Bad Wimpfen, President of the World Federation for Neurorehabilitation and Vice President of the European Federation for Neurorehabilitation",R.drawable.volker_homberg,""))
        Scientific_programme_ArrayList!!.add(Scientific_programme_commitee_model("","Izumi Kondo","General Director of the National Center for Geriatrics and Gerontology (NCGG), Obu, Japan Director of the Assistive Robot Center at the NCGG and Affiliated Professor at Fujita Health University, Nippon Medical School, Toyoake, Japan",R.drawable.izumi_kondo,""))
        Scientific_programme_ArrayList!!.add(Scientific_programme_commitee_model("","Matilde Leonardi","Director of Neurology in the Public Health, Disability Unit and Coma Research Centre at the Carlo Besta Neurological Institute in Milan, Fellow of the European Academy of Neurology and co-chair of the WHO NeuroCOVID Forum group on neurological services for patients recovering from COVID-19",R.drawable.matilde_leonardi,""))
        Scientific_programme_ArrayList!!.add(Scientific_programme_commitee_model("","Mindy Levin","Professor at the School of Physical & Occupational Therapy in the Faculty of Medicine and Health Sciences at McGill University, Montreal, Canada and researcher at the Centre for Interdisciplinary Research in Rehabilitation of Greater Montreal",R.drawable.default_f_image,""))
        Scientific_programme_ArrayList!!.add(Scientific_programme_commitee_model("","Ben Mortenson","Professor and Department Head, Department of Occupational Science and Occupational Therapy at the Faculty of Medicine at the University of British Columbia, Vancouver, Canada",R.drawable.default_m_image,""))
        Scientific_programme_ArrayList!!.add(Scientific_programme_commitee_model("","Dafin Muresanu","Professor of Neurology and Chairman of the Neurosciences Department, University of Medicine and Pharmacy, Cluj-Napoca, Romania, President of the European Federation for Neurorehabilitation",R.drawable.dafin_muresanu,""))
        Scientific_programme_ArrayList!!.add(Scientific_programme_commitee_model("","Mayowa Owolabi","Director, Center for Genomic and Precision Medicine, College of Medicine, University of Ibadan, Nigeria; Hon. Consultant Neurologist, Dept of Medicine, University College Hospital, Ibadan, Nigeria; Foundation Co-Chair, African Stroke Organization; Lead Co-Chair,  WHO-World Stroke Organization -Lancet Neurology Commission on Stroke; Member Board of Dir., World Stroke Organization &  World Hypertension League",R.drawable.mayowa_owolabi,""))
        Scientific_programme_ArrayList!!.add(Scientific_programme_commitee_model("","Nam-Jong Paik","Professor Department of Rehabilitation Medicine at Seoul National University and the Seoul National University Bundang Hospital; President-Elect of the World Federation for Neurorehabilitation",R.drawable.nam_jong_paik,""))
        Scientific_programme_ArrayList!!.add(Scientific_programme_commitee_model("","Caterina Pistarini","Director at the Scientific Institute of Rehabilitation ICS Maugeri, Professor of Physical and Rehabilitation Discipline at the University of Genova, Italy; Secretary General of the World Federation for Neurorehabilitation and Presidium Member of the European Federation of Neurorehabilitation Societies",R.drawable.caterina_pistarini,""))
        Scientific_programme_ArrayList!!.add(Scientific_programme_commitee_model("","Noah Silverberg","Associate Professor, Department of Psychology, University of British Columbia; Principal Investigator, Rehabilitation Research Program, Vancouver Coastal Health Research Institute; Member, Djavad Mowafaghian Centre for Brain Health; Co-Director, Clinical Outcomes for Brain Research – Assessment Hub; Co-Congress Chair of the 13th World Congress for Neurorehabilitation",R.drawable.noah_silverberg,""))
        Scientific_programme_ArrayList!!.add(Scientific_programme_commitee_model("","Abhishek Srivastava","Director, Centre for Physical Medicine, Rehabilitation Consultant and Neuro Rehabilitation Specialist at Kokilaben Dhirubhai Ambani Hospital, Mumbai, India; Honorary Medical Staff Member at Shepherd Medical Center, Atlanta, USA",R.drawable.abhishek_srivastava,""))
        Scientific_programme_ArrayList!!.add(Scientific_programme_commitee_model("","Nirmal Surya","Chairman, Surya Neuro Center, Mumbai, India; Fellow Indian Academy of Neurology; Fellow American Academy of Neurology; President Indian Federation Of Neurorehabilitation (IFNR); Regional Vice President World Federation for Neurorehabilitation ",R.drawable.nirmal_surya,""))
        Scientific_programme_ArrayList!!.add(Scientific_programme_commitee_model("","Andrea Townson","Clinical Associate Professor and Head of the Division of Physical Medicine and Rehabilitation at the University of British Columbia; Medical Site Lead and Attending Physician on the Spinal Cord Injury Rehabilitation Program at GF Strong Rehabilitation Centre, Vancouver, Canada; Investigator at the International Collaboration on Repair Discoveries (ICORD)",R.drawable.default_f_image,""))
        Scientific_programme_ArrayList!!.add(Scientific_programme_commitee_model("","Michael Thaut","Professor at the Faculty of Music and the Faculty of Medicine, Institute of Medical Sciences and Rehabilitation Science Institute at the University of Toronto; Affiliate Scientist St. Michael's Hospital",R.drawable.michael_thaut,""))
        Scientific_programme_ArrayList!!.add(Scientific_programme_commitee_model("","Haiqing Zheng","Professor, Third Affiliated Hospital of Sun Yat-sen University, Guangzhou, China",R.drawable.haiqing_zheng,""))




        val recyclerView1: RecyclerView = findViewById(R.id.RecyclerView_scientific_programme)
        val adapter1 = Scientific_ProgrammeAdapter(this, Scientific_programme_ArrayList!!)
        val layoutManager1 = LinearLayoutManager(this)
        layoutManager1.orientation = LinearLayoutManager.VERTICAL // or HORIZONTAL
        recyclerView1.layoutManager = layoutManager1

        recyclerView1.adapter = adapter1
    }

    override fun onClick(v: View?) {
        when(v!!.id)
        {
            R.id.Event_backbtn->
            {
                val intent=Intent(this,DashboardActivity::class.java)
                startActivity(intent)
            }
        }
    }
}