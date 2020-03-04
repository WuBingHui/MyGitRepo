package com.anthony.wu.my.git.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.anthony.wu.my.git.R
import com.anthony.wu.my.git.base.BaseActivity
import com.anthony.wu.my.git.dto.response.ResposDto
import com.csnt.android_sport.game_detail.adapter.RepositoryViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_repository.*

class RepositoryActivity : BaseActivity() {

    private var resposDto: ResposDto? = null

    private val USER_NAME = "userName"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repository)

        val intent = intent

        resposDto = intent.getSerializableExtra(USER_NAME) as? ResposDto

        initView()

        initTabLayout()


    }


    private fun initView() {

        resposDto?.let {

            repositoryName.text = it.name

            repositoryDescription.text = "Language:${it.language}"

        }


    }

    private fun initTabLayout() {

        resposDto?.let {resposDto->

            val tabFragment = mutableListOf<Fragment>(
                CommitsFragment.newInstance(resposDto.owner.login,resposDto.name),
                CollaboratorsFragment.newInstance(resposDto.owner.login,resposDto.name)
            )

            val repositoryViewPagerAdapter =
                RepositoryViewPagerAdapter(tabFragment, supportFragmentManager, lifecycle)

            repositoryViewPager.adapter = repositoryViewPagerAdapter


            val tabArray = resources.getStringArray(R.array.repository_tab)

            for (i in tabArray.indices) {

                val tab = repositoryTabLayout.newTab()

                repositoryTabLayout.addTab(tab)

            }

            TabLayoutMediator(repositoryTabLayout, repositoryViewPager) { tab, position ->

                tab.text = resources.getStringArray(R.array.repository_tab)[position]

            }.attach()

        }

    }
}
